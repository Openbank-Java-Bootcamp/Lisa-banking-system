package com.ironhack.midtermbankingsystem.service.impl;

import com.ironhack.midtermbankingsystem.DTO.AccountBalanceDTO;
import com.ironhack.midtermbankingsystem.DTO.AccountStatusDTO;
import com.ironhack.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.ironhack.midtermbankingsystem.enums.Status;
import com.ironhack.midtermbankingsystem.model.*;
import com.ironhack.midtermbankingsystem.repository.AccountHolderRepository;
import com.ironhack.midtermbankingsystem.repository.AccountRepository;
import com.ironhack.midtermbankingsystem.repository.AdminRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AdminRepository adminRepository;


    @BeforeEach
    void setUp(){

        Admin admin = new Admin("Admin", "1234", "admin");

        adminRepository.save(admin);

        AccountHolder accountHolder1 = new AccountHolder("Pepito Grillo",
                "1234","grillito", LocalDate.of(1988,12,20)
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        AccountHolder accountHolder2 = new AccountHolder("Lola Flores",
                "1234","flowers", LocalDate.of(2005,12,20)
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));

        CheckingAccount checkingAccount1 = CheckingAccount.fromDTO(new CheckingAccountDTO(BigDecimal.valueOf(7000),
                        Currency.getInstance("EUR"), accountHolder1.getId(), accountHolder2.getId(), "1234")
                , accountHolder1, accountHolder2);


        CheckingAccount checkingAccount2 = CheckingAccount.fromDTO(new CheckingAccountDTO(BigDecimal.valueOf(3000),
                        Currency.getInstance("USD"), accountHolder2.getId(), accountHolder1.getId(), "1234")
                , accountHolder2, accountHolder1);

        accountRepository.saveAll(List.of(checkingAccount1, checkingAccount2));
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void changeAccountStatus() {
        AccountStatusDTO dto = new AccountStatusDTO(Status.FROZEN);
        Integer id = accountRepository.findAll().get(1).getId();
        Account account = accountService.changeAccountStatus(id, dto);
        assertEquals(account.getStatus(), accountRepository.findById(id).get().getStatus());
        assertEquals(Status.FROZEN, accountRepository.findById(id).get().getStatus());
    }

    @Test
    void changeAccountBalance() {
        AccountBalanceDTO  dto = new AccountBalanceDTO(new BigDecimal(2222));
        Integer id = accountRepository.findAll().get(1).getId();
        Account account = accountService.changeAccountBalance(id, dto);
        assertEquals(new BigDecimal(2222).setScale(2, RoundingMode.HALF_EVEN)
                , accountRepository.findById(id).get().getBalance().getAmount());
    }

    @Test
    @WithMockUser(username = "flowers", password = "1234", roles = "ACCOUNT_HOLDER")
    void getAccountBalance() {
        Integer id = accountRepository.findAll().get(1).getId();
        assertEquals(Currency.getInstance("USD"), accountService.getAccountBalance(id).getCurrency());
        assertEquals(new BigDecimal(3000).setScale(2, RoundingMode.HALF_EVEN),
                accountService.getAccountBalance(id).getAmount());
    }
}