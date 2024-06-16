package com.techbellys.serverservice.service.impl;

import com.techbellys.order.*;
import com.techbellys.serverservice.model.Order;
import com.techbellys.serverservice.model.OrderItem;
import com.techbellys.serverservice.model.Product;
import com.techbellys.serverservice.repository.ProductRepository;
import com.techbellys.serverservice.service.OrderServerService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@Slf4j
@AllArgsConstructor
public class OrderGrpcServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    @Autowired
    private OrderServerService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        try {
            List<OrderItem> items = request.getItemsList().stream().map(item -> {
                Product product = productRepository.findById(Long.parseLong(item.getProductId()))
                        .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                return OrderItem.builder()
                        .product(product)
                        .quantity(item.getQuantity())
                        .build();
            }).collect(Collectors.toList());

            Order order = orderService.createOrder(items);
            OrderResponse response = OrderResponse.newBuilder()
                    .setOrderId(order.getId().toString())
                    .addAllItems(order.getItems().stream().map(orderItem ->
                                    OrderItemResponse.newBuilder()
                                            .setProductId(orderItem.getProduct().getId().toString())
                                            .setQuantity(orderItem.getQuantity())
                                            .build())
                            .collect(Collectors.toList()))
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getOrder(GetOrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        try {
            Order order = orderService.getOrderById(Long.parseLong(request.getOrderId()));
            if (order != null) {
                OrderResponse response = OrderResponse.newBuilder()
                        .setOrderId(order.getId().toString())
                        .addAllItems(order.getItems().stream().map(orderItem ->
                                        OrderItemResponse.newBuilder()
                                                .setProductId(orderItem.getProduct().getId().toString())
                                                .setQuantity(orderItem.getQuantity())
                                                .build())
                                .collect(Collectors.toList()))
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(new IllegalArgumentException("Order not found"));
            }
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateOrder(UpdateOrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        try {
            Order order = orderService.getOrderById(Long.parseLong(request.getOrderId()));
            if (order != null) {
                List<OrderItem> updatedItems = request.getItemsList().stream().map(item -> {
                    Product product = productRepository.findById(Long.parseLong(item.getProductId()))
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    return OrderItem.builder()
                            .product(product)
                            .quantity(item.getQuantity())
                            .order(order)
                            .build();
                }).collect(Collectors.toList());

                order.setItems(updatedItems);
                orderService.updateOrder(order);  // Using updateOrder to save updated items

                OrderResponse response = OrderResponse.newBuilder()
                        .setOrderId(order.getId().toString())
                        .addAllItems(updatedItems.stream().map(orderItem ->
                                        OrderItemResponse.newBuilder()
                                                .setProductId(orderItem.getProduct().getId().toString())
                                                .setQuantity(orderItem.getQuantity())
                                                .build())
                                .collect(Collectors.toList()))
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(new IllegalArgumentException("Order not found"));
            }
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void deleteOrder(DeleteOrderRequest request, StreamObserver<EmptyResponse> responseObserver) {
        try {
            orderService.deleteOrder(Long.parseLong(request.getOrderId()));
            responseObserver.onNext(EmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
