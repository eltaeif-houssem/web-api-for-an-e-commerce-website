package com.ecommerce.server.repository;

import com.ecommerce.server.model.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem, Integer> {
}
