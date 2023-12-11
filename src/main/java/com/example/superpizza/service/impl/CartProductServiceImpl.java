package com.example.superpizza.service.impl;

import com.example.superpizza.entity.Cart;
import com.example.superpizza.entity.CartProduct;
import com.example.superpizza.entity.productEntity.Product;
import com.example.superpizza.repository.*;
import com.example.superpizza.service.CartProductService;
import com.example.superpizza.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartProductServiceImpl implements CartProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final CartService cartService;

    @Override
    public void addProductToCart(int productId, int userId) {
        Product chosenProduct = productRepository.findById(productId).get();
        Cart cart = cartRepository.findCartByUserId(userId).get();
        List<CartProduct> products = cart.getCartProducts();
        List<CartProduct> cartProductByCartIdAndProductIdAndOrderStatusIsFalse =
                cartProductRepository.findCartProductByCartIdAndProductIdAndOrderStatusIsFalse(cart.getId(), productId);
        if (!cartProductByCartIdAndProductIdAndOrderStatusIsFalse.isEmpty()) {
            for (CartProduct cartProduct : cartProductByCartIdAndProductIdAndOrderStatusIsFalse) {
                cartProduct.setCountProduct(cartProduct.getCountProduct() + 1);
            }
            cartProductRepository.saveAllAndFlush(cartProductByCartIdAndProductIdAndOrderStatusIsFalse);
        } else {
//            Address address = addressRepository.getAddressesByUserId(userId).get(0);
//            cartService.createOrder(address.getId(), userId);

            CartProduct build = CartProduct.builder()
                    .cart(cart)
                    .product(chosenProduct)
                    .countProduct(1)
//                    .order(orderRepository.findOrderByUserId(userId).get())
//                    .deliveryAddress()
                    .build();
            products.add(build);
            cartProductRepository.save(build);
        }
    }

}
