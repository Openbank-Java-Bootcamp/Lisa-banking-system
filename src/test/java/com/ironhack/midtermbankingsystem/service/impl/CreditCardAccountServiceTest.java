package com.ironhack.midtermbankingsystem.service.impl;

import com.ironhack.midtermbankingsystem.DTO.CreditCardAccountDTO;
import com.ironhack.midtermbankingsystem.model.AccountHolder;
import com.ironhack.midtermbankingsystem.model.Address;
import com.ironhack.midtermbankingsystem.model.CreditCardAccount;
import com.ironhack.midtermbankingsystem.repository.AccountHolderRepository;
import com.ironhack.midtermbankingsystem.repository.CreditCardAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreditCardAccountServiceTest {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    private CreditCardAccountService creditCardAccountService;


    @BeforeEach
    void setUp(){
        AccountHolder accountHolder1 = new AccountHolder("Pepito Grillo",
                "1234","grillito", LocalDate.of(1988,12,20)
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        AccountHolder accountHolder2 = new AccountHolder("Lola Flores",
                "1234","flowers", LocalDate.of(2005,12,20)
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));
        CreditCardAccount creditCardAccount1 = CreditCardAccount.fromDTO(new CreditCardAccountDTO(BigDecimal.valueOf(7000),
                        Currency.getInstance("EUR"), accountHolder1.getId(), accountHolder2.getId(), "1234"
                        , null, null), accountHolder1, accountHolder2);


        CreditCardAccount creditCardAccount2 = CreditCardAccount.fromDTO(new CreditCardAccountDTO(BigDecimal.valueOf(3000),
                        Currency.getInstance("USD"), accountHolder2.getId(), accountHolder1.getId()
                        , "1234", null, null), accountHolder2, accountHolder1);

        creditCardAccountRepository.saveAll(List.of(creditCardAccount1, creditCardAccount2));
    }
    @AfterEach
    void tearDown() {
        creditCardAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void addInterest() {
        CreditCardAccount cardAccount = creditCardAccountRepository.findAll().get(1);
        cardAccount.setDateInterestAdded(LocalDate.of(2022,03,22));
        creditCardAccountRepository.save(cardAccount);

        creditCardAccountService.addInterest(cardAccount);

        assertEquals(new BigDecimal(4200).setScale(2, RoundingMode.HALF_EVEN)
                , creditCardAccountRepository.findById(cardAccount.getId()).get().getBalance().getAmount());
    }
}