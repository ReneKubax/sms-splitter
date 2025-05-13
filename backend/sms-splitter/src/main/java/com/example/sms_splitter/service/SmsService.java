package com.example.sms_splitter.service;

import com.example.sms_splitter.dto.SmsRequest;
import com.example.sms_splitter.dto.SmsResponse;
import com.example.sms_splitter.exception.InvalidSmsException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmsService {
    
    private static final int MAX_SMS_LENGTH = 160;
    private static final String SUFFIX_FORMAT = " - Part %d of %d";
    
    public SmsResponse processSms(SmsRequest request) {
        String message = request.getMessage();
        
        if (message == null || message.trim().isEmpty()) {
            throw new InvalidSmsException("Message cannot be empty");
        }
        
        List<String> parts = splitMessage(message);
        
        System.out.println("=== SMS Messages ===");
        for (int i = 0; i < parts.size(); i++) {
            System.out.println("Part " + (i + 1) + ": " + parts.get(i));
        }
        System.out.println("==================");
        
        return SmsResponse.builder()
                .originalMessage(message)
                .totalParts(parts.size())
                .parts(parts)
                .totalCharacters(message.length())
                .build();
    }
    
    private List<String> splitMessage(String message) {
        List<String> parts = new ArrayList<>();
        
        if (message.length() <= MAX_SMS_LENGTH) {
            parts.add(message);
            return parts;
        }
        
        int estimatedParts = calculateNumberOfParts(message);
        
        int currentIndex = 0;
        for (int i = 1; i <= estimatedParts; i++) {
            String suffix = String.format(SUFFIX_FORMAT, i, estimatedParts);
            int maxContentLength = MAX_SMS_LENGTH - suffix.length();
            
            if (maxContentLength <= 0) {
                throw new InvalidSmsException("Suffix is too long for SMS length limit");
            }
            
            int endIndex = Math.min(currentIndex + maxContentLength, message.length());
            
            if (endIndex < message.length() && endIndex > currentIndex) {
                int lastSpace = message.lastIndexOf(' ', endIndex);
                if (lastSpace > currentIndex) {
                    endIndex = lastSpace;
                }
            }
            
            String content = message.substring(currentIndex, endIndex);
            parts.add(content + suffix);
            
            currentIndex = endIndex;
            if (currentIndex >= message.length()) {
                break;
            }
            
            while (currentIndex < message.length() && message.charAt(currentIndex) == ' ') {
                currentIndex++;
            }
        }
        
        if (parts.size() != estimatedParts) {
            return recalculateParts(message, parts.size());
        }
        
        return parts;
    }
    
    private int calculateNumberOfParts(String message) {
        int parts = 1;
        int remainingLength = message.length();
        
        while (remainingLength > 0) {
            String suffix = String.format(SUFFIX_FORMAT, parts, parts);
            int availableLength = MAX_SMS_LENGTH - suffix.length();
            
            if (availableLength <= 0) {
                throw new InvalidSmsException("Message is too long to split");
            }
            
            if (remainingLength <= availableLength) {
                break;
            }
            
            remainingLength -= availableLength;
            parts++;
            
            if (parts > 1000) {
                throw new InvalidSmsException("Message is too long to process");
            }
        }
        
        return parts;
    }
    
    private List<String> recalculateParts(String message, int actualParts) {
        List<String> parts = new ArrayList<>();
        int currentIndex = 0;
        
        for (int i = 1; i <= actualParts; i++) {
            String suffix = String.format(SUFFIX_FORMAT, i, actualParts);
            int maxContentLength = MAX_SMS_LENGTH - suffix.length();
            
            int endIndex = Math.min(currentIndex + maxContentLength, message.length());
            
            if (endIndex < message.length() && endIndex > currentIndex) {
                int lastSpace = message.lastIndexOf(' ', endIndex);
                if (lastSpace > currentIndex) {
                    endIndex = lastSpace;
                }
            }
            
            String content = message.substring(currentIndex, endIndex);
            parts.add(content + suffix);
            
            currentIndex = endIndex;
            
            while (currentIndex < message.length() && message.charAt(currentIndex) == ' ') {
                currentIndex++;
            }
        }
        
        return parts;
    }
}