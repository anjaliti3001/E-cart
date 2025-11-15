package com.example.E_Kart.model.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
        String Id,
        String customerName,
        String email,
        String status,
        LocalDate orderDate,
        List<OrderItemResponse> items) {
}
