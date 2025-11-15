package com.example.E_Kart.model.dto;

import java.util.List;

public record OrderRequest(
        String customerName,
        String email,
        List<OrderItemRecord> items
) {
}
