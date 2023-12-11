package com.example.superpizza.entity;

import com.example.superpizza.entity.userEntity.Address;
import com.example.superpizza.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private User user;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_address")
    private Address deliveryAddress;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date dateTime;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<CartProduct> cartProduct;
    private boolean isPaymentDone;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private double orderTotalCost;
}
