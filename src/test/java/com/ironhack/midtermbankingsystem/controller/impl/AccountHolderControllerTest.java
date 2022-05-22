package com.ironhack.midtermbankingsystem.controller.impl;

import com.ironhack.midtermbankingsystem.DTO.AccountHolderDTO;
import com.ironhack.midtermbankingsystem.model.AccountHolder;
import com.ironhack.midtermbankingsystem.model.Address;
import com.ironhack.midtermbankingsystem.model.Admin;
import com.ironhack.midtermbankingsystem.repository.AccountHolderRepository;
import com.ironhack.midtermbankingsystem.repository.AdminRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountHolderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AdminRepository adminRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        objectMapper.registerModule(new JavaTimeModule());

        Admin admin = new Admin("Admin", "1234", "admin");

        adminRepository.save(admin);

        AccountHolder accountHolder1 = new AccountHolder("Pepito Grillo",
                 "1234","grillito", LocalDate.of(1988, 12, 20)
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        AccountHolder accountHolder2 = new AccountHolder("Lola Flores",
                 "1234","flowers", LocalDate.of(2005, 12, 20)
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void saveAccountHolder() throws Exception {

        String body = objectMapper.writeValueAsString( new AccountHolderDTO("Test Holder", "test",
                "1234", LocalDate.of(1988,12,20)
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                ,new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")));

        MvcResult result = mockMvc.perform(post("/api/account-holders/admin/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Test Holder"));

        assertEquals(3, accountHolderRepository.findAll().size());
    }

    @Test
    void deleteAccountHolder() throws Exception {

        AccountHolder foundAccountHolder = accountHolderRepository.findAll().get(1);

        MvcResult result = mockMvc.perform(delete("/api/account-holders/admin/delete/{id}", foundAccountHolder
                        .getId())).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Lola Flores"));

        assertEquals(1, accountHolderRepository.findAll().size());
    }
}