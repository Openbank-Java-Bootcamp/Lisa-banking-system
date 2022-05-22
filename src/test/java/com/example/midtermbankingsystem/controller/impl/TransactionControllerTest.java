package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.DTO.CheckingAccountDTO;
import com.example.midtermbankingsystem.DTO.SavingsAccountDTO;
import com.example.midtermbankingsystem.DTO.TransactionDTO;
import com.example.midtermbankingsystem.model.*;
import com.example.midtermbankingsystem.repository.AccountHolderRepository;
import com.example.midtermbankingsystem.repository.AccountRepository;
import com.example.midtermbankingsystem.repository.AdminRepository;
import com.example.midtermbankingsystem.repository.TransactionRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TransactionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


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

        CheckingAccount checkingAccount1 = CheckingAccount.fromDTO(new CheckingAccountDTO(BigDecimal.valueOf(8000),
                        Currency.getInstance("EUR"), accountHolder1.getId(), null, "1234")
                , accountHolder1, accountHolder2);


        CheckingAccount checkingAccount2 = CheckingAccount.fromDTO(new CheckingAccountDTO(BigDecimal.valueOf(6000),
                        Currency.getInstance("EUR"), accountHolder2.getId(), null, "1234")
                , accountHolder2, accountHolder1);

        accountRepository.saveAll(List.of(checkingAccount1, checkingAccount2));

        Transaction transaction1 = Transaction.fromDTO(new TransactionDTO(BigDecimal.valueOf(800),
                Currency.getInstance("EUR"), checkingAccount1.getId(), checkingAccount2.getId(),"Lola Flores",
                null, null, null), Optional.of(checkingAccount1), Optional.of(checkingAccount2));

        Transaction transaction2 = Transaction.fromDTO(new TransactionDTO(BigDecimal.valueOf(600),
                Currency.getInstance("EUR"),  checkingAccount2.getId(),checkingAccount1.getId(),"Pepito Grillo",
                null, null, null), Optional.of(checkingAccount2), Optional.of(checkingAccount1));

        transactionRepository.saveAll(List.of(transaction1, transaction2));
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        adminRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "flowers", password = "1234", roles = "ACCOUNT_HOLDER")
    void saveTransaction() throws Exception {
        String body = objectMapper.writeValueAsString(new TransactionDTO(BigDecimal.valueOf(200),
                Currency.getInstance("EUR"),accountRepository.findAll().get(1).getId(), accountRepository.findAll()
                .get(0).getId(),"Pepito Grillo",null,null,null));

        MvcResult result = mockMvc.perform(post("/api/transactions/account-holder/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("EUR"));

        assertEquals(3, transactionRepository.findAll().size());
    }

}