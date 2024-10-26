package com.ecommerce.server.model;


import com.ecommerce.server.enums.CategoryName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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

    private Double price;

    @Enumerated(EnumType.STRING)
    private CategoryName category;

    private String brand;

    private Integer stockQuantity;

    private String imageURL;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartItem> lineItems;

    @ManyToMany(mappedBy = "savings")
    @JsonIgnore
    private List<User> users = new ArrayList<>();
}
