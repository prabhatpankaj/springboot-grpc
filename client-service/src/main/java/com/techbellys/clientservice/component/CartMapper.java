package com.techbellys.clientservice.component;

import com.techbellys.cart.*;
import com.techbellys.clientservice.dto.CartClientRequest;
import com.techbellys.clientservice.dto.CartClientResponse;
import com.techbellys.clientservice.dto.CartItemClientRequest;
import com.techbellys.clientservice.dto.CartItemClientResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CreateCartRequest toServerRequest(CartClientRequest cartClientRequest) {
        return CreateCartRequest.newBuilder()
                .setUserId(cartClientRequest.getUserId())
                .build();
    }

    public AddItemToCartRequest toServerAddItemRequest(String cartId, CartItemClientRequest cartItemClientRequest) {
        return AddItemToCartRequest.newBuilder()
                .setCartId(cartId)
                .setProductId(cartItemClientRequest.getProductId())
                .setQuantity(cartItemClientRequest.getQuantity())
                .build();
    }

    public UpdateCartItemRequest toServerUpdateItemRequest(String cartId, String cartItemId, Integer quantity) {
        return UpdateCartItemRequest.newBuilder()
                .setCartId(cartId)
                .setCartItemId(cartItemId)
                .setQuantity(quantity)
                .build();
    }

    public CartClientResponse toClientResponse(CartResponse cartResponse) {
        return CartClientResponse.builder()
                .cartId(cartResponse.getCartId())
                .userId(cartResponse.getUserId())
                .items(cartResponse.getItemsList().stream()
                        .map(this::toCartItemResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private CartItemClientResponse toCartItemResponse(CartItemResponse item) {
        return CartItemClientResponse.builder()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .build();
    }
}
