package com.ecommerce.server.controller;

import com.ecommerce.server.model.ShoppingCart;
import com.ecommerce.server.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/admin")
    public ResponseEntity<List<ShoppingCart>> getShoppingCarts(){
        List<ShoppingCart> shoppingCarts = shoppingCartService.getShoppingCarts();
        return ResponseEntity.ok(shoppingCarts);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable Integer id){
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartById(id);
        return ResponseEntity.ok(shoppingCart);
    }

    @GetMapping
    public ResponseEntity<ShoppingCart> getUserShoppingCart(){
        ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart();
        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addProductToShoppingCart(@PathVariable Integer id){
        shoppingCartService.addProductToShoppingCart(id);
        return ResponseEntity.ok("product added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeProductFromShoppingCart(@PathVariable Integer id){
        shoppingCartService.removeProductFromShoppingCart(id);
        return ResponseEntity.ok("product removed");
    }

    @PostMapping("/quantity/{id}")
    public ResponseEntity<String> increaseProductQuantity(@PathVariable Integer id){
        shoppingCartService.increaseProductQuantity(id);
        return ResponseEntity.ok("product quantity increased");
    }

    @DeleteMapping("/quantity/{id}")
    public ResponseEntity<String> decreaseProductQuantity(@PathVariable Integer id){
        shoppingCartService.decreaseProductQuantity(id);
        return ResponseEntity.ok("product quantity decreased");
    }
}
