package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.DTO.CreditCardAccountDTO;
import com.example.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.example.midtermbankingsystem.model.*;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.AdminRepository;
import com.example.midtermbankingsystem.repository.CreditCardAccountRepository;
import com.example.midtermbankingsystem.repository.SavingsAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SavingsAccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private AdminRepository adminRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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

        SavingsAccount savingsAccount1 = SavingsAccount.fromDTO(new SavingsAccountDTO(BigDecimal.valueOf(1000),
                        Currency.getInstance("EUR"), accountHolder1.getId(), accountHolder2.getId(), "1234"
                        , null, new BigDecimal(700))
                , accountHolder1, accountHolder2);


        SavingsAccount savingsAccount2 = SavingsAccount.fromDTO(new SavingsAccountDTO(BigDecimal.valueOf(6000),
                        Currency.getInstance("USD"), accountHolder2.getId(), accountHolder1.getId(), "1234"
                        , new BigDecimal(200), null)
                , accountHolder2, accountHolder1);

        savingsAccountRepository.saveAll(List.of(savingsAccount1, savingsAccount2));
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
        savingsAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void saveSavingsAccount() throws Exception {
        String body = objectMapper.writeValueAsString( new SavingsAccountDTO(BigDecimal.valueOf(6000),
                Currency.getInstance("USD"), accountHolderRepository.findAll().get(0).getId(),
                accountHolderRepository.findAll().get(1).getId(), "1234",new BigDecimal(400),null));

        MvcResult result = mockMvc.perform(post("/api/savings-accounts/admin/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("USD"));

        assertEquals(3, savingsAccountRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    void getSavingsAccountById() throws Exception {

        SavingsAccount foundSavingsAccount = savingsAccountRepository.findAll().get(0);

        MvcResult result = mockMvc.perform(get("/api/savings-accounts/{id}", foundSavingsAccount
                .getId())).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("100"));

        assertEquals("grillito", savingsAccountRepository.findById(foundSavingsAccount.getId()).get()
                .getPrimaryOwner().getUsername());
    }
}