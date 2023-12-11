package com.example.superpizza.service.impl;

import com.example.superpizza.entity.CourierDelivery;
import com.example.superpizza.entity.Order;
import com.example.superpizza.entity.OrderStatus;
import com.example.superpizza.repository.CourierDeliveryRepository;
import com.example.superpizza.repository.OrderRepository;
import com.example.superpizza.service.CourierDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierDeliveryServiceImpl implements CourierDeliveryService {
    private final CourierDeliveryRepository courierDeliveryRepository;
    private final OrderRepository orderRepository;

    @Override
    public void takeDeliveryOrderById(int deliveryId, int courierId) {
        Optional<Order> orderById = orderRepository.findById(deliveryId);
        if (orderById.isPresent()) {
            CourierDelivery build = CourierDelivery.builder()
                    .order(orderById.get())
                    .courierId(courierId)
                    .orderStatus(OrderStatus.IN_PROCESS)
                    .build();
            courierDeliveryRepository.save(build);
            orderById.get().setOrderStatus(OrderStatus.IN_PROCESS);
            orderRepository.save(orderById.get());
        }
    }

    @Override
    public List<CourierDelivery> getDeliveriesByCourier(int id, OrderStatus orderStatus) {
        return courierDeliveryRepository.findAllByCourierIdAndOrderStatus(id, orderStatus);
    }

    @Override
    public void changeDeliveryOrderStatusByOrderId(int productId) {
        List<CourierDelivery> courierDeliveryByOrderId = courierDeliveryRepository.findCourierDeliveryByOrder_Id(productId);
        for (CourierDelivery courierDelivery : courierDeliveryByOrderId) {
            courierDelivery.setOrderStatus(OrderStatus.DELIVERED);
            courierDeliveryRepository.save(courierDelivery);
        }
    }

}
