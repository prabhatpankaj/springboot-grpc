package com.techbellys.serverservice.service.impl;

import com.techbellys.serverservice.model.Cart;
import com.techbellys.serverservice.model.CartItem;
import com.techbellys.serverservice.repository.CartItemRepository;
import com.techbellys.serverservice.repository.CartRepository;
import com.techbellys.serverservice.service.CartServerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CartServerServiceImpl implements CartServerService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart addItemToCart(Long cartId, Long productId, int quantity) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cart.getItems().add(cartItemRepository.save(cartItem));
            return cartRepository.save(cart);
        } else {
            throw new IllegalArgumentException("Cart not found");
        }
    }

    @Override
    public Cart updateCartItem(Long cartId, Long cartItemId, int quantity) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            for (CartItem item : cart.getItems()) {
                if (item.getId().equals(cartItemId)) {
                    item.setQuantity(quantity);
                    cartItemRepository.save(item);
                    return cartRepository.save(cart);
                }
            }
            throw new IllegalArgumentException("Cart item not found");
        } else {
            throw new IllegalArgumentException("Cart not found");
        }
    }

    @Override
    public void deleteCartItem(Long cartId, Long cartItemId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
            cartItemRepository.deleteById(cartItemId);
            cartRepository.save(cart);
        } else {
            throw new IllegalArgumentException("Cart not found");
        }
    }

    @Override
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}
