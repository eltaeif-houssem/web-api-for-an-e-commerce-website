package com.ecommerce.server.controller;


import com.ecommerce.server.model.Order;
import com.ecommerce.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(){
        orderService.createOrder();
        return ResponseEntity.ok("Order created successfully");
    }

    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(){
        List<Order> orders = orderService.getUserOrders();
        return ResponseEntity.ok(orders);
    }
}
