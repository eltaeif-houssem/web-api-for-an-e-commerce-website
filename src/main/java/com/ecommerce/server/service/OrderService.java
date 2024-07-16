package com.ecommerce.server.service;

import com.ecommerce.server.enums.OrderStatus;
import com.ecommerce.server.exception.NotFoundException;
import com.ecommerce.server.model.*;
import com.ecommerce.server.repository.OrderRepository;
import com.ecommerce.server.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final PaymentRepository paymentRepository;

    private List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    private List<Order> getAllUserOrders(){
        User user = userService.getCurrentUser();
        return user.getOrders();
    }

    private Order getOrder(Integer orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new NotFoundException("Order with the id "+orderId+" was not found");
        }
        return order.get();
    }

 /*   private void createOrder(Address address){
        ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart();
        Payment payment = Payment.builder()

        Order order = Order.builder()
                .ordered(LocalDateTime.now())
                .shipTo(address)
                .status(OrderStatus.NEW)
                .lineItems(shoppingCart.getLineItems())
                .build();
    }*/
}
