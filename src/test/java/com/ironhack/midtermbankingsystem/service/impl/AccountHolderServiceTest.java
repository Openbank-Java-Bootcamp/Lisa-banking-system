package com.ironhack.midtermbankingsystem.service.impl;

import com.ironhack.midtermbankingsystem.DTO.AccountHolderDTO;
import com.ironhack.midtermbankingsystem.model.AccountHolder;
import com.ironhack.midtermbankingsystem.model.Address;
import com.ironhack.midtermbankingsystem.repository.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class AccountHolderServiceTest {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AccountHolderService accountHolderService;

    @BeforeEach
    void setUp() {
        AccountHolder accountHolder1 = new AccountHolder("Pepito Grillo",
                 "1234","grillito", LocalDate.of(1988,12,20)
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        AccountHolder accountHolder2 = new AccountHolder("Lola Flores",
                 "1234","flowers", LocalDate.of(2005,12,20)
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
    }

    @Test
    void saveAccountHolder() {

        AccountHolderDTO dto = new AccountHolderDTO("Test Holder",
                "test", "1234", LocalDate.of(1988,12,20)
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        AccountHolder accountHolder = accountHolderService.saveAccountHolder(dto);

        Assertions.assertEquals(accountHolderRepository.findById(accountHolder.getId()).get().getUsername(), accountHolder.getUsername());
    }

}