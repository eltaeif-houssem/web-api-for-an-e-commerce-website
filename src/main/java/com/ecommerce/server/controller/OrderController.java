package com.ecommerce.server.controller;


import com.ecommerce.server.enums.OrderStatus;
import com.ecommerce.server.enums.PaymentStatus;
import com.ecommerce.server.model.Order;
import com.ecommerce.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/admin")
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer id){
        Order order = orderService.getOrder(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/admin/order-status/{id}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Integer id, @RequestParam OrderStatus orderStatus){
        orderService.updateOrderStatus(id,orderStatus);
        return ResponseEntity.ok("Order status updated successfully!");
    }

    @PutMapping("/admin/payment-status/{id}")
    public ResponseEntity<String> updateOrderPaymentStatus(@PathVariable Integer id, @RequestParam PaymentStatus paymentStatus){
        orderService.updateOrderPaymentStatus(id, paymentStatus);
        return ResponseEntity.ok("Order payment status updated successfully!");
    }

    @PutMapping("/admin/payment-success/{id}")
    public ResponseEntity<String> addOrderPayment(@PathVariable Integer id){
        orderService.addPaymentDate(id);
        return ResponseEntity.ok("Order payment success!");
    }


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
