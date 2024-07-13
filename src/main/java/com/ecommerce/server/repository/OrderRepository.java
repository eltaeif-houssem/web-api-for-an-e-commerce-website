package com.ecommerce.server.repository;

import com.ecommerce.server.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
