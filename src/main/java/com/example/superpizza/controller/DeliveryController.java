package com.example.superpizza.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.superpizza.entity.CourierDelivery;
import com.example.superpizza.entity.Order;
import com.example.superpizza.entity.OrderStatus;
import com.example.superpizza.security.CurrentUser;
import com.example.superpizza.service.CourierDeliveryService;
import com.example.superpizza.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courier")
public class DeliveryController {
    private final OrderService orderService;
    private final CourierDeliveryService courierDeliveryService;

    @GetMapping("/delivery")
    public String getDeliveryPage(ModelMap modelMap,
                                  @RequestParam Optional<Integer> size,
                                  @RequestParam Optional<Integer> page) {
        Page<Order> unDeliveredOrders = orderService.getUnDeliveredOrders(size, page);

        if (unDeliveredOrders.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, unDeliveredOrders.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        modelMap.addAttribute("unDeliveredOrders", unDeliveredOrders);
        return "deliveryPage";
    }

    @GetMapping("/delivery/take/id={id}")
    public String takeDeliveryOrder(@PathVariable("id") int deliveryId, @AuthenticationPrincipal CurrentUser currentUser) {
        courierDeliveryService.takeDeliveryOrderById(deliveryId, currentUser.getUser().getId());
        return "redirect:/courier/delivery";
    }

    @GetMapping("/my_deliveries")
    public String showMyDeliveries(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<CourierDelivery> courierDeliveries = courierDeliveryService.getDeliveriesByCourier(currentUser.getUser().getId(), OrderStatus.IN_PROCESS);
        modelMap.addAttribute("courierDeliveries", courierDeliveries);
        return "myDeliveries";
    }

    @GetMapping("/order_is_delivered/id={id}")
    public String changeDeliveryOrderStatus(@PathVariable("id") int orderId) {
        courierDeliveryService.changeDeliveryOrderStatusByOrderId(orderId);
        return "redirect:/courier/my_deliveries";
    }

}
