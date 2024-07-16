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
    public ResponseEntity<Object> addProductToShoppingCart(@PathVariable Integer id){
        shoppingCartService.addProductToShoppingCart(id);
        return ResponseEntity.ok("product added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeProductFromShoppingCart(@PathVariable Integer id){
        shoppingCartService.removeProductFromShoppingCart(id);
        return ResponseEntity.ok("product removed");
    }

    @PostMapping("/quantity/{id}")
    public ResponseEntity<Object> increaseProductQuantity(@PathVariable Integer id){
        shoppingCartService.increaseProductQuantity(id);
        return ResponseEntity.ok("product quantity increased");
    }

    @DeleteMapping("/quantity/{id}")
    public ResponseEntity<Object> decreaseProductQuantity(@PathVariable Integer id){
        shoppingCartService.decreaseProductQuantity(id);
        return ResponseEntity.ok("product quantity decreased");
    }
}
