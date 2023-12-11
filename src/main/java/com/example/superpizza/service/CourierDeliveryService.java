package com.example.superpizza.service;

import com.example.superpizza.entity.CourierDelivery;
import com.example.superpizza.entity.OrderStatus;

import java.util.List;

public interface CourierDeliveryService {
    void takeDeliveryOrderById(int deliveryId, int courierId);

    List<CourierDelivery> getDeliveriesByCourier(int id, OrderStatus orderStatus);

    void changeDeliveryOrderStatusByOrderId(int orderId);
}
