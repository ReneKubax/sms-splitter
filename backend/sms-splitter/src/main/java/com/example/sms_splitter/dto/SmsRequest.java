package com.example.sms_splitter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequest {
    
    @NotBlank(message = "Message cannot be blank")
    private String message;
    
    private String phoneNumber; // Opcional, ya que no se envía realmente
}