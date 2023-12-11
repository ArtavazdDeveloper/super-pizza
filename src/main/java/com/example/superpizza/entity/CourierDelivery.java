package com.example.superpizza.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "courier_deliveries")
@Entity
public class CourierDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Order order;
    private int courierId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
