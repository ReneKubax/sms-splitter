package com.example.sms_splitter.controller;

import com.example.sms_splitter.dto.SmsRequest;
import com.example.sms_splitter.service.SmsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SmsController.class)
class SmsControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private SmsService smsService;
    
    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/sms/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("SMS Splitter API"));
    }
    
    @Test
    void testSendSmsValidRequest() throws Exception {
        SmsRequest request = new SmsRequest("Test message", "123456789");
        
        mockMvc.perform(post("/api/v1/sms/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
    
    @Test
    void testSendSmsInvalidRequest() throws Exception {
        SmsRequest request = new SmsRequest("", null);
        
        mockMvc.perform(post("/api/v1/sms/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testSendSmsNullMessage() throws Exception {
        String jsonRequest = "{\"phoneNumber\": \"123456789\"}";
        
        mockMvc.perform(post("/api/v1/sms/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }
}