package com.ironhack.midtermbankingsystem.service.impl;


import com.ironhack.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.ironhack.midtermbankingsystem.DTO.TransactionDTO;
import com.ironhack.midtermbankingsystem.model.*;
import com.ironhack.midtermbankingsystem.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TransactionServiceTest {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private TransactionService transactionService;


    @BeforeEach
    void setUp() {

        Admin admin = new Admin("Admin", "1234", "admin");

        adminRepository.save(admin);

        AccountHolder accountHolder1 = new AccountHolder("Pepito Grillo",
                "1234", "grillito", LocalDate.of(1988, 12, 20)
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        AccountHolder accountHolder2 = new AccountHolder("Lola Flores",
                "1234", "flowers", LocalDate.of(2005, 12, 20)
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));

        ThirdParty thirdParty = new ThirdParty("Third Party", "party", "1234",
                "secretkey", 1);

        thirdPartyRepository.save(thirdParty);

        CheckingAccount checkingAccount1 = CheckingAccount.fromDTO(new CheckingAccountDTO(BigDecimal.valueOf(8000),
                        Currency.getInstance("EUR"), accountHolder1.getId(), null, "1234")
                , accountHolder1, accountHolder2);


        CheckingAccount checkingAccount2 = CheckingAccount.fromDTO(new CheckingAccountDTO(BigDecimal.valueOf(6000),
                        Currency.getInstance("EUR"), accountHolder2.getId(), null, "1234")
                , accountHolder2, accountHolder1);

        accountRepository.saveAll(List.of(checkingAccount1, checkingAccount2));

        Transaction transaction1 = Transaction.fromDTO(new TransactionDTO(BigDecimal.valueOf(800),
                Currency.getInstance("EUR"), checkingAccount1.getId(), checkingAccount2.getId(), "Lola Flores",
                null, null, null), Optional.of(checkingAccount1), Optional.of(checkingAccount2));

        Transaction transaction2 = Transaction.fromDTO(new TransactionDTO(BigDecimal.valueOf(600),
                Currency.getInstance("EUR"), checkingAccount2.getId(), checkingAccount1.getId(), "Pepito Grillo",
                null, null, null), Optional.of(checkingAccount2), Optional.of(checkingAccount1));

        Transaction transaction3 = Transaction.fromDTO(new TransactionDTO(BigDecimal.valueOf(600),
                Currency.getInstance("EUR"), checkingAccount2.getId(), null, "Third Party",
                null, "1234", 1), Optional.of(checkingAccount2), null);

        Transaction transaction4 = Transaction.fromDTO(new TransactionDTO(BigDecimal.valueOf(800),
                Currency.getInstance("EUR"), checkingAccount1.getId(), checkingAccount2.getId(), "Lola Flores",
                null, null, null), Optional.of(checkingAccount1), Optional.of(checkingAccount2));


        transactionRepository.saveAll(List.of(transaction1, transaction2, transaction3, transaction4));
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        adminRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void processTransaction() {
        //todo assert throws error if null payer and target
    }

    @Test
    void validateThirdPartyTargetName() {

        Transaction thirdPartyTransaction = transactionRepository.findAll().get(2);
        thirdPartyTransaction.setTargetName("Error Name");
        transactionRepository.save(thirdPartyTransaction);

        var thrown = assertThrows(ResponseStatusException.class, () ->
        {transactionService.validateThirdPartyTargetName(thirdPartyTransaction, 1);});

        assertEquals("400 BAD_REQUEST \"Transaction failed, invalid Target Name\"", thrown.getMessage());
    }

    @Test
    void validateSecretKey() {

        Transaction transaction = transactionRepository.findAll().get(1);
        transaction.setSecretKey("Error Key");
        transactionRepository.save(transaction);

        var thrown = assertThrows(ResponseStatusException.class, () ->
        {transactionService.validateSecretKey(transaction.getSecretKey(), transaction.getTargetAcc());});

        assertEquals("400 BAD_REQUEST \"Transaction failed, invalid Secret Key\"", thrown.getMessage());
    }

    @Test
    void applyTransaction() {

        transactionService.applyTransaction(new BigDecimal(500),
                Optional.ofNullable(accountRepository.findAll().get(0)),
                Optional.ofNullable(accountRepository.findAll().get(1)));

        assertEquals(new BigDecimal(7500).setScale(2, RoundingMode.HALF_EVEN)
                , accountRepository.findAll().get(0).getBalance().getAmount());

        assertEquals(new BigDecimal(6500).setScale(2, RoundingMode.HALF_EVEN)
                , accountRepository.findAll().get(1).getBalance().getAmount());
    }

    @Test
    void findMinimumBalanceAndApplyFee() {
        Account checkingAccount = accountRepository.findAll().get(1);
        checkingAccount.setBalance(new Money(new BigDecimal(200), Currency.getInstance("EUR")));
        accountRepository.save(checkingAccount);
        transactionService.findMinimumBalanceAndApplyFee(checkingAccount);

        assertEquals(new BigDecimal(160).setScale(2, RoundingMode.HALF_EVEN),
                accountRepository.findAll().get(1).getBalance().getAmount());

    }

    @Test
    //@WithMockUser(username = "flowers", password = "1234", roles = "ACCOUNT_HOLDER")
    void validateTimeSinceLastTransactions() {
        Account checkingAccount = accountRepository.findAll().get(1);
        List<Transaction> transactions =  transactionRepository.findByPayerAccOrderByTransferDate(checkingAccount);
        Transaction lastTransaction = transactions.get(transactions.size() - 1);
        lastTransaction.setTransferDate(Instant.now());
        transactionRepository.save(lastTransaction);

        var thrown = assertThrows(ResponseStatusException.class, () ->
        {transactionService.validateTimeSinceLastTransactions(checkingAccount);});

        assertEquals("400 BAD_REQUEST \"Transaction failed, fraud detected as your last transaction was less " +
                "than a second ago. Account frozen\"", thrown.getMessage());
    }

    @Test
    void validateNotOverDailyLimit() {
        Account checkingAccount = accountRepository.findAll().get(1);
        List<Transaction> transactions =  transactionRepository.findByPayerAccOrderByTransferDate(checkingAccount);

        Transaction anotherTransaction = transactions.get(transactions.size() - 2);
        LocalDate date = LocalDate.parse("2022-05-20");
        Instant instant = date.atStartOfDay(ZoneOffset.UTC).toInstant();
        anotherTransaction.setTransferDate(instant);

        Transaction lastTransaction = transactions.get(transactions.size() - 1);
        lastTransaction.setAmount(new Money(new BigDecimal(10000)));

        transactionRepository.saveAll(List.of(anotherTransaction, lastTransaction));

        var thrown = assertThrows(ResponseStatusException.class, () ->
        {transactionService.validateNotOverDailyLimit(checkingAccount, lastTransaction);});

        assertEquals("400 BAD_REQUEST \"Transaction failed, fraud detected as transactions in the las 24 hours " +
                "are higher than 150% of your usual daily transactions. Account frozen\"", thrown.getMessage());
    }
}