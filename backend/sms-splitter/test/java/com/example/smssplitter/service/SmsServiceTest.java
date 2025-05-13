package com.example.sms_splitter.controller;

import com.example.sms_splitter.dto.SmsRequest;
import com.example.sms_splitter.dto.SmsResponse;
import com.example.sms_splitter.exception.InvalidSmsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmsServiceTest {
    
    private SmsService smsService;
    
    @BeforeEach
    void setUp() {
        smsService = new SmsService();
    }
    
    @Test
    void testShortMessage() {
        SmsRequest request = new SmsRequest("Hello World!", null);
        SmsResponse response = smsService.processSms(request);
        
        assertEquals(1, response.getTotalParts());
        assertEquals("Hello World!", response.getParts().get(0));
    }
    
    @Test
    void testLongMessageSplit() {
        String longMessage = "This is a very long message that definitely exceeds the 160 character limit " +
                "and will need to be split into multiple parts. We need to make sure that the splitting " +
                "works correctly and adds the proper suffixes to each part.";
        
        SmsRequest request = new SmsRequest(longMessage, null);
        SmsResponse response = smsService.processSms(request);
        
        assertTrue(response.getTotalParts() > 1);
        
        // Verificar que cada parte no exceda 160 caracteres
        for (String part : response.getParts()) {
            assertTrue(part.length() <= 160);
        }
        
        // Verificar que las partes tengan el sufijo correcto
        for (int i = 0; i < response.getParts().size(); i++) {
            String part = response.getParts().get(i);
            String expectedSuffix = String.format(" - Part %d of %d", i + 1, response.getTotalParts());
            assertTrue(part.endsWith(expectedSuffix));
        }
    }
    
    @Test
    void testEmptyMessage() {
        SmsRequest request = new SmsRequest("", null);
        assertThrows(InvalidSmsException.class, () -> smsService.processSms(request));
    }
    
    @Test
    void testNullMessage() {
        SmsRequest request = new SmsRequest(null, null);
        assertThrows(InvalidSmsException.class, () -> smsService.processSms(request));
    }
    
    @Test
    void testExactly160Characters() {
        String message = "a".repeat(160);
        SmsRequest request = new SmsRequest(message, null);
        SmsResponse response = smsService.processSms(request);
        
        assertEquals(1, response.getTotalParts());
        assertEquals(message, response.getParts().get(0));
    }
    
    @Test
    void testWordBoundaryRespect() {
        // Mensaje diseñado para probar que no se cortan palabras
        String message = "This is a test message with words that should not be split in the middle. " +
                "We want to make sure that the algorithm respects word boundaries when splitting messages.";
        
        SmsRequest request = new SmsRequest(message, null);
        SmsResponse response = smsService.processSms(request);
        
        // Verificar que ninguna parte termine con una palabra cortada
        for (String part : response.getParts()) {
            // Quitar el sufijo para verificar el contenido
            int suffixStart = part.lastIndexOf(" - Part");
            if (suffixStart > 0) {
                String content = part.substring(0, suffixStart);
                // El contenido no debe terminar con una palabra cortada
                assertTrue(content.trim().matches(".*\\b\\w+$"));
            }
        }
    }
}