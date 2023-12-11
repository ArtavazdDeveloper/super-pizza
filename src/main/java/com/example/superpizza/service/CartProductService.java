package com.example.superpizza.service;

public interface CartProductService {
    void addProductToCart(int productId, int userId);

//    List<CartProduct> getProductsByCartId(Optional<Cart> cartByUserId);
}
