package com.example.E_Kart.controller;

import com.example.E_Kart.model.dto.OrderRequest;
import com.example.E_Kart.model.dto.OrderResponse;
import com.example.E_Kart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return  new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        List<OrderResponse> responses = orderService.getAllOrderResponses();
        return  new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
