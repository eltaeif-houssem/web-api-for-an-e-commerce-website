package com.ecommerce.server.service;

import com.ecommerce.server.exception.NotFoundException;
import com.ecommerce.server.model.ShoppingCart;
import com.ecommerce.server.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart getShoppingCartById(Integer id){
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(id);
        if(shoppingCart.isEmpty()){
            throw new NotFoundException("shopping cart with the id "+id+" was not found");
        }
        return shoppingCart.get();
    }
}
