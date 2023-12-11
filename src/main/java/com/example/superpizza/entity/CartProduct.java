package com.example.superpizza.entity;

import com.example.superpizza.entity.productEntity.Product;
import com.example.superpizza.entity.userEntity.Address;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cart_products")
@Entity
@ToString(exclude = {"cart", "order"})
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne
    private Order order;

    private int countProduct;
    @Column(name = "order_status")
    private boolean orderStatus;
    @OneToOne
    private Address deliveryAddress;

}
