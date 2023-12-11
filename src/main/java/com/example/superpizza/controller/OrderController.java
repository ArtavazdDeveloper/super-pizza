package com.example.superpizza.controller;


import com.example.superpizza.entity.Cart;
import com.example.superpizza.security.CurrentUser;
import com.example.superpizza.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final CartService cartService;

    @GetMapping()
    public String getOrderPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        Optional<Cart> cartByUserId = cartService.getCartByUserId(currentUser.getUser().getId());
        cartByUserId.ifPresent(cart -> modelMap.addAttribute("cartProducts", cart.getCartProducts()));
        cartByUserId.ifPresent(cart -> modelMap.addAttribute("totalCoast", cartService.totalCost(cart)));
        return "checkout";
    }

    @GetMapping("/to_pay")
    public String payToOrder(@AuthenticationPrincipal CurrentUser currentUser) {
        cartService.payForOrder(currentUser.getUser().getId());
        return "redirect:/";
    }

}
