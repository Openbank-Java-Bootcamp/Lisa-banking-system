package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.DTO.CreditCardAccountDTO;
import com.example.midtermbankingsystem.model.*;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.AccountRepository;
import com.example.midtermbankingsystem.repository.AdminRepository;
import com.example.midtermbankingsystem.repository.CreditCardAccountRepository;
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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CreditCardAccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

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
                 "1234", "grillito",LocalDate.of(1988, 12, 20)
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        AccountHolder accountHolder2 = new AccountHolder("Lola Flores",
                 "1234", "flowers", LocalDate.of(2005, 12, 20)
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK")
                , new Address("Fake Street 13", "Faketown", "Fakeland", "F23AK"));

        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));

        CreditCardAccount creditCardAccount1 = CreditCardAccount.fromDTO(new CreditCardAccountDTO(BigDecimal.valueOf(1000),
                        Currency.getInstance("EUR"), accountHolder1.getId(), accountHolder2.getId(), "1234"
                        , null, new BigDecimal(700))
                , accountHolder1, accountHolder2);


        CreditCardAccount creditCardAccount2 = CreditCardAccount.fromDTO(new CreditCardAccountDTO(BigDecimal.valueOf(6000),
                        Currency.getInstance("USD"), accountHolder2.getId(), accountHolder1.getId(), "1234"
                        , new BigDecimal(200), null)
                , accountHolder2, accountHolder1);

        creditCardAccountRepository.saveAll(List.of(creditCardAccount1, creditCardAccount2));
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
        creditCardAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void saveCreditCardAccount() throws Exception {
        String body = objectMapper.writeValueAsString( new CreditCardAccountDTO(BigDecimal.valueOf(6000),
                Currency.getInstance("USD"), 2, 3, "1234"
                , new BigDecimal(400), null));

        MvcResult result = mockMvc.perform(post("/api/credit-card-accounts/admin/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("400"));

        assertEquals(3, creditCardAccountRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    void getCreditCardAccountById() throws Exception {

        CreditCardAccount foundCreditCardAccount = creditCardAccountRepository.findAll().get(0);

        MvcResult result = mockMvc.perform(get("/api/credit-card-accounts/{id}", foundCreditCardAccount
                        .getId())).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("100"));

        assertEquals("grillito", creditCardAccountRepository.findById(foundCreditCardAccount.getId()).get()
                .getPrimaryOwner().getUsername());
    }
}