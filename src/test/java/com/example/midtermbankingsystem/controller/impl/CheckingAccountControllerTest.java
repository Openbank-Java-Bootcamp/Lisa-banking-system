package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.DTO.AdminDTO;
import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.model.AccountHolder;
import com.example.midtermbankingsystem.model.Address;
import com.example.midtermbankingsystem.model.CheckingAccount;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.AccountRepository;
import com.example.midtermbankingsystem.repository.CheckingAccountRepository;
import com.example.midtermbankingsystem.repository.StudentCheckingAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CheckingAccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        AccountHolder accountHolder1 = new AccountHolder("Pepito Grillo",
                 "1234","grillito", LocalDate.of(1988, 12, 20)
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        AccountHolder accountHolder2 = new AccountHolder("Lola Flores",
                 "1234","flowers", LocalDate.of(2005, 12, 20)
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));

        CheckingAccount checkingAccount1 = CheckingAccount.fromDTO(new CheckingAccountDTO(BigDecimal.valueOf(3000),
                        Currency.getInstance("EUR"), accountHolder1.getId(), accountHolder2.getId(), "1234")
                , accountHolder1, accountHolder2);


        CheckingAccount checkingAccount2 = CheckingAccount.fromDTO(new CheckingAccountDTO(BigDecimal.valueOf(3000),
                        Currency.getInstance("EUR"), accountHolder1.getId(), null, "1234")
                , accountHolder2, accountHolder1);

        checkingAccountRepository.saveAll(List.of(checkingAccount1, checkingAccount2));
    }

    @AfterEach
    void tearDown() {
        studentCheckingAccountRepository.deleteAll();
        checkingAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();;
    }

    @Test
    void saveCheckingAccount_PrimaryOwnerOlderThanTwentyFour() throws Exception {

        String body = objectMapper.writeValueAsString( new CheckingAccountDTO(BigDecimal.valueOf(700),
                Currency.getInstance("EUR"), accountHolderRepository.findAll().get(0).getId(),null, "1234"));

        MvcResult result = mockMvc.perform(post("/api/checking-accounts/admin/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Pepito Grillo"));

        assertEquals(3, checkingAccountRepository.findAll().size());
    }


    @Test
    void saveCheckingAccountAsStudentCheckingAccount_PrimaryOwnerYoungerThanTwentyFour() throws Exception {
        String body = objectMapper.writeValueAsString( new CheckingAccountDTO(BigDecimal.valueOf(700),
                Currency.getInstance("EUR"), accountHolderRepository.findAll().get(1).getId(),null, "1234"));

        MvcResult result = mockMvc.perform(post("/api/checking-accounts/admin/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Lola Flores"));

        assertEquals(1, studentCheckingAccountRepository.findAll().size());
    }
}