package com.example.superpizza.controller;


import com.example.superpizza.entity.Cart;
import com.example.superpizza.entity.CartProduct;
import com.example.superpizza.security.CurrentUser;
import com.example.superpizza.service.AddressService;
import com.example.superpizza.service.CartProductService;
import com.example.superpizza.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final CartProductService cartProductService;
    private final AddressService addressService;

    @GetMapping("/{id}")
    public String hasUserCart(@PathVariable("id") int id,
                              @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            Optional<Cart> cartByUserId = cartService.getCartByUserId(currentUser.getUser().getId());
            if (cartByUserId.isEmpty()) {
                Cart cart = new Cart();
                cart.setUser(currentUser.getUser());
                cartService.addCart(cart);
            }
        }
        return "redirect:/cart/add/" + id;
    }

    @GetMapping("/add/{id}")
    public String addProductToCart(@PathVariable("id") int productId,
                                   @AuthenticationPrincipal CurrentUser currentUser) {

        if (currentUser != null) {
            int userId = currentUser.getUser().getId();
            cartProductService.addProductToCart(productId, userId);
        }
        return "redirect:/";
    }


    @GetMapping("/cartProductList")
    public String getOrderList(@AuthenticationPrincipal CurrentUser currentUser,
                               ModelMap modelMap) {
        Optional<Cart> cartByUserId = cartService.getCartByUserId(currentUser.getUser().getId());
        if (cartByUserId.isPresent()) {
            Cart cart = cartByUserId.get();
            List<CartProduct> cartProductList = cartService.getUserCartProductList(cart.getId());

            modelMap.addAttribute("productList", cartProductList);
            double totalCost = cartService.totalCost(cart);
            modelMap.addAttribute("totalCost", totalCost);
        }

        modelMap.addAttribute("userAddress", addressService.getAddressesByUserId(currentUser.getUser().getId()));
        return "cart";
    }


    @GetMapping("/delete_product/{id}")
    public String deleteProduct(@PathVariable("id") int id,
                                @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Cart> cartByUserId = cartService.getCartByUserId(currentUser.getUser().getId());
        if (cartByUserId.isPresent()) {
            Cart cart = cartByUserId.get();
            cartService.deleteProductById(cart, id);
        }
        return "redirect:/cart/cartProductList";
    }

    @GetMapping("/order/{id}")
    public String getCartOrder(@PathVariable("id") int checkedAddressId, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            cartService.addDeliveryAddressToCart(checkedAddressId, currentUser.getUser().getId());
            return "redirect:/order";
        }

        return "redirect:/";
    }
}
