package com.ecommerce.server.controller;

import com.ecommerce.server.model.ShoppingCart;
import com.ecommerce.server.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<Object> getUserShoppingCart(){
        ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart();
        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> addProduct(@PathVariable Integer id){
        shoppingCartService.addProductToShoppingCart(id);
        return ResponseEntity.ok("product added");
    }
}
