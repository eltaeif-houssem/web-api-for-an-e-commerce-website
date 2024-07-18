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
        User user = userService.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.NEW);
        order.setOrdered(LocalDateTime.now());
        order.setShipTo(user.getAddress());

        Double totalPrice = 0.0;
        for(CartItem cartItem:shoppingCart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            totalPrice+= cartItem.getQuantity() * cartItem.getProduct().getPrice();
            cartItem.setShoppingCart(null);
        }

        order.setOrderItems(orderItems);
        order.setTotal(totalPrice);
        shoppingCart.getCartItems().clear();

        shoppingCartRepository.save(shoppingCart);
        orderRepository.save(order);
    }
}
