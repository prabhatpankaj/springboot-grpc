package com.techbellys.serverservice.service;

import com.techbellys.serverservice.model.Cart;

import java.util.List;

public interface CartServerService {
    Cart createCart(Long userId);

    Cart getCartById(Long id);

    Cart addItemToCart(Long cartId, Long productId, int quantity);

    Cart updateCartItem(Long cartId, Long cartItemId, int quantity);

    void deleteCartItem(Long cartId, Long cartItemId);

    void deleteCart(Long cartId);

    List<Cart> getAllCarts();
}