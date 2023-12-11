package com.example.superpizza.repository;

import com.example.superpizza.entity.CourierDelivery;
import com.example.superpizza.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierDeliveryRepository extends JpaRepository<CourierDelivery, Integer> {
    List<CourierDelivery> findAllByCourierIdAndOrderStatus(int courierId, OrderStatus orderStatus);

    List<CourierDelivery> findCourierDeliveryByOrder_Id(int orderId);
}
