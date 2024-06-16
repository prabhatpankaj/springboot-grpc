package com.techbellys.serverservice.service.impl;


import com.techbellys.cart.*;
import com.techbellys.serverservice.model.Cart;
import com.techbellys.serverservice.service.CartServerService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
@AllArgsConstructor
public class CartGrpcServiceImpl extends CartServiceGrpc.CartServiceImplBase {

    private final CartServerService cartServerService;

    @Override
    public void createCart(CreateCartRequest request, StreamObserver<CartResponse> responseObserver) {
        Cart cart = cartServerService.createCart(request.getUserId());
        CartResponse response = CartResponse.newBuilder()
                .setCartId(cart.getId().toString())
                .setUserId(cart.getUserId())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCart(GetCartRequest request, StreamObserver<CartResponse> responseObserver) {
        Cart cart = cartServerService.getCartById(Long.parseLong(request.getCartId()));
        if (cart != null) {
            CartResponse response = CartResponse.newBuilder()
                    .setCartId(cart.getId().toString())
                    .setUserId(cart.getUserId())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new IllegalArgumentException("Cart not found"));
        }
    }

    @Override
    public void addItemToCart(AddItemToCartRequest request, StreamObserver<CartResponse> responseObserver) {
        Cart cart = cartServerService.addItemToCart(Long.parseLong(request.getCartId()), request.getProductId(), request.getQuantity());
        CartResponse response = CartResponse.newBuilder()
                .setCartId(cart.getId().toString())
                .setUserId(cart.getUserId())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateCartItem(UpdateCartItemRequest request, StreamObserver<CartResponse> responseObserver) {
        Cart cart = cartServerService.updateCartItem(Long.parseLong(request.getCartId()), Long.parseLong(request.getCartItemId()), request.getQuantity());
        CartResponse response = CartResponse.newBuilder()
                .setCartId(cart.getId().toString())
                .setUserId(cart.getUserId())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCartItem(DeleteCartItemRequest request, StreamObserver<EmptyResponse> responseObserver) {
        cartServerService.deleteCartItem(Long.parseLong(request.getCartId()), Long.parseLong(request.getCartItemId()));
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCart(DeleteCartRequest request, StreamObserver<EmptyResponse> responseObserver) {
        cartServerService.deleteCart(Long.parseLong(request.getCartId()));
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}