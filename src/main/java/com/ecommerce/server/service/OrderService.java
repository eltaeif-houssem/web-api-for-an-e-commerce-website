package com.ecommerce.server.service;

import com.ecommerce.server.enums.OrderStatus;
import com.ecommerce.server.exception.NotFoundException;
import com.ecommerce.server.model.*;
import com.ecommerce.server.repository.CartItemRepository;
import com.ecommerce.server.repository.OrderRepository;
import com.ecommerce.server.repository.PaymentRepository;
import com.ecommerce.server.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final PaymentRepository paymentRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository lineItemRepository;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getUserOrders(){
        User user = userService.getCurrentUser();
        return user.getOrders();
    }

    public Order getOrder(Integer orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new NotFoundException("Order with the id "+orderId+" was not found");
        }
        return order.get();
    }

    public void createOrder(){

    }
}
