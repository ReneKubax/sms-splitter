package com.example.sms_splitter.controller;

import com.example.sms_splitter.dto.SmsRequest;
import com.example.sms_splitter.dto.SmsResponse;
import com.example.sms_splitter.service.SmsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sms")
@CrossOrigin(origins = "*")
public class SmsController {
    
    private final SmsService smsService;
    
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }
    
    @PostMapping("/send")
    public ResponseEntity<SmsResponse> sendSms(@Valid @RequestBody SmsRequest request) {
        SmsResponse response = smsService.processSms(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "SMS Splitter API");
        health.put("version", "1.0.0");
        return ResponseEntity.ok(health);
    }
}