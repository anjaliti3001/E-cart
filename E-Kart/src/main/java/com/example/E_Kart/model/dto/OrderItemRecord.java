package com.example.E_Kart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record OrderItemRecord(
        int productID,
        int quantity
) {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequestDto {
        private String username;
        private String password;
    }
}
