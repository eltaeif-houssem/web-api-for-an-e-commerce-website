package com.ecommerce.server.controller;


import com.ecommerce.server.model.Product;
import com.ecommerce.server.service.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("saving")
@RequiredArgsConstructor
public class SavingController {
    private final SavingService savingService;

    @GetMapping
    public ResponseEntity<List<Product>> getUserSaving(){
        List<Product> savings = savingService.getUserSavings();
        return ResponseEntity.ok(savings);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addProductToSaving(@PathVariable Integer id){
        savingService.addProductToSavings(id);
        return ResponseEntity.ok("Product added successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductFromSaving(@PathVariable Integer id){
        savingService.removeProductFromSavings(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
