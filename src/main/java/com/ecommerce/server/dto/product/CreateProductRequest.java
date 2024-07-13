package com.ecommerce.server.dto.product;

import com.ecommerce.server.enums.CategoryName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class CreateProductRequest {
    @NotEmpty(message = "Name is required")
    @NotNull(message = "Name is required")
    private String name;

    @NotEmpty(message = "Description is required")
    @NotNull(message = "Description is required")
    private String description;

    @NotEmpty(message = "Price is required")
    @NotNull(message = "Price is required")
    @PositiveOrZero
    private Float price;

    @NotEmpty(message = "Category is required")
    @NotNull(message = "Category is required")
    private CategoryName categoryName;

    @NotEmpty(message = "Brand is required")
    @NotNull(message = "Brand is required")
    private String brand;

    @NotEmpty(message = "Stock quantity is required")
    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero
    private Integer stockQuantity;

    @NotEmpty(message = "Image is required")
    @NotNull(message = "Image is required")
    private MultipartFile image;
}
