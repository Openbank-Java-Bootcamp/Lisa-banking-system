package com.ironhack.midtermbankingsystem.service.impl;

import com.ironhack.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.ironhack.midtermbankingsystem.model.AccountHolder;
import com.ironhack.midtermbankingsystem.model.Address;
import com.ironhack.midtermbankingsystem.model.SavingsAccount;
import com.ironhack.midtermbankingsystem.repository.AccountHolderRepository;
import com.ironhack.midtermbankingsystem.repository.SavingsAccountRepository;
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
class SavingsAccountServiceTest {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private SavingsAccountService savingsAccountService;


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
        SavingsAccount savingsAccount1 = SavingsAccount.fromDTO(new SavingsAccountDTO(BigDecimal.valueOf(7000),
                Currency.getInstance("EUR"), accountHolder1.getId(), accountHolder2.getId(), "1234"
                , null, null), accountHolder1, accountHolder2);


        SavingsAccount savingsAccount2 = SavingsAccount.fromDTO(new SavingsAccountDTO(BigDecimal.valueOf(3000),
                Currency.getInstance("USD"), accountHolder2.getId(), accountHolder1.getId()
                , "1234", null, null), accountHolder2, accountHolder1);

        savingsAccountRepository.saveAll(List.of(savingsAccount1, savingsAccount2));
    }
    @AfterEach
    void tearDown() {
        savingsAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void addInterest() {
        SavingsAccount savingsAccount = savingsAccountRepository.findAll().get(0);
        savingsAccount.setDateInterestAdded(LocalDate.of(2020,05,21));
        savingsAccountRepository.save(savingsAccount);

        savingsAccountService.addInterest(savingsAccount);

        assertEquals(new BigDecimal(7035).setScale(2, RoundingMode.HALF_EVEN)
                , savingsAccountRepository.findById(savingsAccount.getId()).get().getBalance().getAmount());
    }
}