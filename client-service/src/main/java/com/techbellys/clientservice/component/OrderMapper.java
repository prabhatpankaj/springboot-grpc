package com.techbellys.clientservice.component;

import com.techbellys.clientservice.dto.OrderClientRequest;
import com.techbellys.clientservice.dto.OrderClientResponse;
import com.techbellys.clientservice.dto.OrderItemClient;
import com.techbellys.order.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public CreateOrderRequest toServerRequest(OrderClientRequest orderClientRequest) {
        return CreateOrderRequest.newBuilder()
                .addAllItems(orderClientRequest.getItems().stream().map(this::toOrderItem).collect(Collectors.toList()))
                .build();
    }

    public UpdateOrderRequest toServerUpdateRequest(String id, OrderClientRequest orderClientRequest) {
        return UpdateOrderRequest.newBuilder()
                .setOrderId(id)
                .addAllItems(orderClientRequest.getItems().stream().map(this::toOrderItem).collect(Collectors.toList()))
                .build();
    }

    private OrderItem toOrderItem(OrderItemClient itemClient) {
        return OrderItem.newBuilder()
                .setProductId(itemClient.getProductId())
                .setQuantity(itemClient.getQuantity())
                .build();
    }

    public OrderClientResponse toOrderClientResponse(OrderResponse orderResponse) {
        return OrderClientResponse.builder()
                .orderId(orderResponse.getOrderId())
                .items(orderResponse.getItemsList().stream()
                        .map(this::toOrderItemClient)
                        .collect(Collectors.toList()))
                .build();
    }

    private OrderItemClient toOrderItemClient(OrderItemResponse item) {
        return OrderItemClient.builder()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .build();
    }
}
