package com.example.midtermbankingsystem.service.impl;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.model.Address;
import com.example.midtermbankingsystem.model.CheckingAccount;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

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

        CheckingAccount checkingAccount1 = CheckingAccount.fromDTO(new CheckingAccountDTO(BigDecimal.valueOf(3000),
                        Currency.getInstance("EUR"), accountHolder1.getId(), accountHolder2.getId(), "1234")
                , accountHolder1, accountHolder2);


        CheckingAccount checkingAccount2 = CheckingAccount.fromDTO(new CheckingAccountDTO(BigDecimal.valueOf(3000),
                        Currency.getInstance("EUR"), accountHolder2.getId(), accountHolder1.getId(), "1234")
                , accountHolder2, accountHolder1);

        accountRepository.saveAll(List.of(checkingAccount1, checkingAccount2));
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void changeAccountStatus() {
    }

    @Test
    void changeAccountBalance() {
    }

    @Test
    void getAccountBalance() {
    }
}