package com.ecommerce.server.service;

import com.ecommerce.server.dto.product.CreateProductRequest;
import com.ecommerce.server.dto.product.UpdateProductRequest;
import com.ecommerce.server.exception.NotFoundException;
import com.ecommerce.server.model.Product;
import com.ecommerce.server.repository.ProductRepository;
import com.ecommerce.server.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


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
                    .category(request.getCategory())
                    .price(request.getPrice())
                    .stockQuantity(request.getStockQuantity())
                    .build();

            String imageURL = fileUtil.uploadFile(request.getImage());
            product.setImageURL(imageURL);

            productRepository.save(product);
        }
    }

    public Product getProduct(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()){
            throw new NotFoundException("Product with id "+productId+" not found");
        }
        return product.get();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public void updateProduct(UpdateProductRequest request, Integer productId) throws IOException {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()){
            Product updatedProduct = product.get();
            updatedProduct.setName(request.getName());
            updatedProduct.setDescription(request.getDescription());
            updatedProduct.setBrand(request.getBrand());
            updatedProduct.setCategory(request.getCategory());
            updatedProduct.setPrice(request.getPrice());
            updatedProduct.setStockQuantity(request.getStockQuantity());
            if (request.getImage() != null && !request.getImage().isEmpty()) {
                fileUtil.deleteFromLocation(updatedProduct.getImageURL());
                String imageURL = fileUtil.uploadFile(request.getImage());
                updatedProduct.setImageURL(imageURL);
            }
            productRepository.save(updatedProduct);
        }
    }

}