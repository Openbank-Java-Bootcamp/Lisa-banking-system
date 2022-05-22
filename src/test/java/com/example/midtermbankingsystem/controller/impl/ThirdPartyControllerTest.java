package com.example.midtermbankingsystem.controller.impl;

import com.example.midtermbankingsystem.DTO.AdminDTO;
import com.example.midtermbankingsystem.DTO.ThirdPartyDTO;
import com.example.midtermbankingsystem.model.ThirdParty;
import com.example.midtermbankingsystem.repository.ThirdPartyRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ThirdPartyControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void saveThirdParty() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String body = objectMapper.writeValueAsString( new ThirdPartyDTO("Third Party", "party"
                , "1234", "secretkey", 1));

        MvcResult result = mockMvc.perform(post("/api/third-parties/admin/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("party"));

        assertEquals(1, thirdPartyRepository.findAll().size());

        thirdPartyRepository.deleteAll();
    }
}