package com.example.sms_splitter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsResponse {
    
    private String originalMessage;
    private int totalParts;
    private List<String> parts;
    private int totalCharacters;
}