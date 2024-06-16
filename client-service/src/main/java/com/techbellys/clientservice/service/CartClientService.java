package com.techbellys.clientservice.service;


import com.techbellys.cart.*;
import com.techbellys.clientservice.component.CartMapper;
import com.techbellys.clientservice.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CartClientService {

    @GrpcClient("CartService")
    private CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub;

    private final CartMapper cartMapper;

    public CartClientResponse createCart(CartClientRequest cartClientRequest) {
        CreateCartRequest request = cartMapper.toServerRequest(cartClientRequest);
        CartResponse response = cartServiceBlockingStub.createCart(request);
        return cartMapper.toClientResponse(response);
    }

    public CartClientResponse getCart(String cartId) {
        GetCartRequest request = GetCartRequest.newBuilder().setCartId(cartId).build();
        CartResponse response = cartServiceBlockingStub.getCart(request);
        return cartMapper.toClientResponse(response);
    }

    public CartClientResponse addItemToCart(String cartId, CartItemClientRequest cartItemClientRequest) {
        AddItemToCartRequest request = cartMapper.toServerAddItemRequest(cartId, cartItemClientRequest);
        CartResponse response = cartServiceBlockingStub.addItemToCart(request);
        return cartMapper.toClientResponse(response);
    }

    public CartClientResponse updateCartItem(String cartId, String cartItemId, Integer quantity) {
        UpdateCartItemRequest request = cartMapper.toServerUpdateItemRequest(cartId, cartItemId, quantity);
        CartResponse response = cartServiceBlockingStub.updateCartItem(request);
        return cartMapper.toClientResponse(response);
    }

    public void deleteCartItem(String cartId, String cartItemId) {
        try {
            DeleteCartItemRequest request = DeleteCartItemRequest.newBuilder()
                    .setCartId(cartId)
                    .setCartItemId(cartItemId)
                    .build();
            EmptyResponse response = cartServiceBlockingStub.deleteCartItem(request);
        } catch (Exception e) {
            log.error("Error deleting product with cartItemId {}: {}", cartItemId, e.getMessage());
            throw e;
        }
    }

    public void deleteCart(String cartId) {
        try {
            DeleteCartRequest request = DeleteCartRequest.newBuilder()
                    .setCartId(cartId)
                    .build();
            EmptyResponse response = cartServiceBlockingStub.deleteCart(request);
        } catch (Exception e) {
            log.error("Error deleting product with cartItemId {}: {}", cartId, e.getMessage());
            throw e;
        }
    }
}
