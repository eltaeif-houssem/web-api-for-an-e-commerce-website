package com.ecommerce.server.controller;

import com.ecommerce.server.dto.product.CreateProductRequest;
import com.ecommerce.server.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.badRequest().body("An error has occurred: "+exception.getMessage());
        }
    }
}
