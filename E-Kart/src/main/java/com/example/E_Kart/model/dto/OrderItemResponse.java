package com.example.E_Kart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public record OrderItemResponse(
        String ProductName,
        int quantity,
        BigDecimal totalPrice
) {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponseDto {
        String jwt;
        Long userId;
    }
}
