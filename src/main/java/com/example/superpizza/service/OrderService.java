package com.example.superpizza.service;


import com.example.superpizza.entity.Order;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderService {


    void save(Order order);

    Optional<Order> getOrderByUserId(int id);

    Page<Order> getUnDeliveredOrders(Optional<Integer> size, Optional<Integer> page);
}
