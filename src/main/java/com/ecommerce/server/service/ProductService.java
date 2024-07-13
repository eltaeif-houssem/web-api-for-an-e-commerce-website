package com.ecommerce.server.service;

import com.ecommerce.server.dto.product.CreateProductRequest;
import com.ecommerce.server.model.Product;
import com.ecommerce.server.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private static final String UPLOAD_DIR = "uploads/";


    public void createProduct(CreateProductRequest request) throws IOException {
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            Product product = Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .brand(request.getBrand())
                    .categoryName(request.getCategoryName())
                    .price(request.getPrice())
                    .stockQuantity(request.getStockQuantity())
                    .build();

            byte[] bytes = request.getImage().getBytes();
            Path path = Paths.get(UPLOAD_DIR + request.getImage().getOriginalFilename() + LocalDateTime.now());
            Files.write(path, bytes);
            product.setImageURL(path.toString());

            productRepository.save(product);
        }
    }
}
