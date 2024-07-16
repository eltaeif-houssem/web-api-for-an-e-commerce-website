package com.ecommerce.server.controller;

import com.ecommerce.server.dto.product.CreateProductRequest;
import com.ecommerce.server.dto.product.UpdateProductRequest;
import com.ecommerce.server.model.Product;
import com.ecommerce.server.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(path = "/admin/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createProduct(@ModelAttribute @Valid CreateProductRequest request){
        try{
            productService.createProduct(request);
            return ResponseEntity.ok("Product created successfully");
        }catch (Exception exception){
            return ResponseEntity.badRequest().body("An error has occurred: " + exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        List<Product> products = productService.getProducts(page, size).toList();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(
            @PathVariable Integer id
    ){
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<String> updateProduct(@ModelAttribute @Valid UpdateProductRequest request, @PathVariable Integer id){
        try{
            productService.updateProduct(request, id);
            return ResponseEntity.ok("Product updated successfully");
        }catch (Exception exception){
            return ResponseEntity.badRequest().body("An error has occurred: " + exception.getMessage());
        }
    }
}