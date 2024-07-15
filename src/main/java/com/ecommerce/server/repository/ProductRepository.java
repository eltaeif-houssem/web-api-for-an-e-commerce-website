package com.ecommerce.server.repository;

import com.ecommerce.server.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
