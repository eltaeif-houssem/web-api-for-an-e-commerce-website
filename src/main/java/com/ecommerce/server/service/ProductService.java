package com.ecommerce.server.service;

import com.ecommerce.server.dto.product.CreateProductRequest;
import com.ecommerce.server.model.Product;
import com.ecommerce.server.repository.ProductRepository;
import com.ecommerce.server.util.FileUtil;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import static java.io.File.separator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final FileUtil fileUtil;


    public void createProduct(CreateProductRequest request) throws IOException {
        if (!request.getImage().isEmpty()) {
            Product product = Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .brand(request.getBrand())
                    .categoryName(request.getCategoryName())
                    .price(request.getPrice())
                    .stockQuantity(request.getStockQuantity())
                    .build();

            byte[] bytes = request.getImage().getBytes();
            String path = fileUtil.uploadFile(request.getImage());
            product.setImageURL(path);

            productRepository.save(product);
        }
    }

}
