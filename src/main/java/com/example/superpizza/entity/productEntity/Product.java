package com.example.superpizza.entity.productEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private double price;
    private double volume;
    @Column(name = "product_image")
    private String productImg;
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;
}
