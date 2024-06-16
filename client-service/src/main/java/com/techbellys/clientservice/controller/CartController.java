package com.techbellys.clientservice.controller;
import com.techbellys.clientservice.dto.CartClientRequest;
import com.techbellys.clientservice.dto.CartClientResponse;
import com.techbellys.clientservice.dto.CartItemClientRequest;
import com.techbellys.clientservice.service.CartClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@AllArgsConstructor
@Slf4j
public class CartController {

    private final CartClientService cartClientService;

    @PreAuthorize("hasAnyRole('client')")
    @PostMapping
    public ResponseEntity<CartClientResponse> createCart(@RequestBody CartClientRequest cartClientRequest) {
        CartClientResponse response = cartClientService.createCart(cartClientRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartClientResponse> getCart(@PathVariable String cartId) {
        return new ResponseEntity<>(cartClientService.getCart(cartId), HttpStatus.OK);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartClientResponse> addItemToCart(@PathVariable String cartId, @RequestBody CartItemClientRequest cartItemClientRequest) {
        return new ResponseEntity<>(cartClientService.addItemToCart(cartId , cartItemClientRequest), HttpStatus.OK);
    }

    @PutMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<CartClientResponse> updateCartItem(@PathVariable String cartId, @PathVariable String cartItemId, @RequestBody Integer quantity) {
        return new ResponseEntity<>(cartClientService.updateCartItem(cartId,cartItemId,quantity), HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable String cartId, @PathVariable String cartItemId) {
        cartClientService.deleteCartItem(cartId, cartItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable String cartId) {
        cartClientService.deleteCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

