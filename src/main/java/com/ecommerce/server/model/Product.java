package com.ecommerce.server.model;

import com.ecommerce.server.enums.CategoryName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Float price;

    @Enumerated(EnumType.STRING)
    private CategoryName categoryName;

    private String brand;

    private Integer stockQuantity;

    private String imageURL;

    @OneToMany(fetch = LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private List<LineItem> lineItems;
}
