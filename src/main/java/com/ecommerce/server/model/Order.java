package com.ecommerce.server.model;

import com.ecommerce.server.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime ordered;

    private LocalDateTime shipped;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Float total;

    @OneToOne(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "ship_to", referencedColumnName = "id")
    private Address shipTo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
