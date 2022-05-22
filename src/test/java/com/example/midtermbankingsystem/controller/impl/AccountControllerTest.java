package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.DTO.AccountBalanceDTO;
import com.example.midtermbankingsystem.DTO.AccountStatusDTO;
import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.enums.Status;
import com.example.midtermbankingsystem.model.*;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.AccountRepository;
import com.example.midtermbankingsystem.repository.AdminRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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
        adminRepository.deleteAll();
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
//    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    void patchAccountStatus() throws Exception {
        String body = objectMapper.writeValueAsString(new AccountStatusDTO(Status.FROZEN));

        Account foundCheckingAccount = accountRepository.findAll().get(1);

        MvcResult result = mockMvc.perform(patch("/api/accounts/admin/status/{id}", foundCheckingAccount
                .getId()).content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("FROZEN"));
        assertEquals(Status.FROZEN, accountRepository.findById(foundCheckingAccount.getId()).get().getStatus());
    }

    @Test
    void patchAccountBalance() throws Exception {
        String body = objectMapper.writeValueAsString(new AccountBalanceDTO(new BigDecimal(2222)));

        Account foundCheckingAccount = accountRepository.findAll().get(1);

        MvcResult result = mockMvc.perform(patch("/api/accounts/admin/balance/{id}", foundCheckingAccount
                .getId()).content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("2222"));

        assertEquals(new BigDecimal(2222).setScale(2, RoundingMode.HALF_EVEN),
                accountRepository.findById(foundCheckingAccount.getId()).get().getBalance().getAmount());
    }

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    void getAccountBalance() throws Exception {

        Account foundCheckingAccount = accountRepository.findAll().get(1);

        MvcResult result = mockMvc.perform(get("/api/accounts/balance/{id}", foundCheckingAccount.getId()))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("3000"));

        assertEquals(new BigDecimal(3000).setScale(2, RoundingMode.HALF_EVEN),
                accountRepository.findById(foundCheckingAccount.getId()).get().getBalance().getAmount());
    }

    @Test
    void deleteAccount() throws Exception {
        Account foundCheckingAccount = accountRepository.findAll().get(1);

        MvcResult result = mockMvc.perform(delete("/api/accounts/admin/delete/{id}", foundCheckingAccount
                        .getId())).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("grillito"));

        assertEquals(1, accountRepository.findAll().size());
    }
}