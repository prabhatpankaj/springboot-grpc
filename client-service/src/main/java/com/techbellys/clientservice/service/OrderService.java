package com.techbellys.clientservice.service;

import com.techbellys.clientservice.component.OrderMapper;
import com.techbellys.clientservice.dto.OrderClientResponse;
import com.techbellys.order.*;
import com.techbellys.order.EmptyResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    @GrpcClient("orderService")
    private OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

    private final OrderMapper orderMapper;

    public OrderClientResponse createOrder(CreateOrderRequest createOrderRequest) {
        OrderResponse orderResponse = orderServiceBlockingStub.createOrder(createOrderRequest);
        return orderMapper.toOrderClientResponse(orderResponse);
    }

    public OrderClientResponse getOrder(GetOrderRequest getOrderRequest) {
        OrderResponse orderResponse = orderServiceBlockingStub.getOrder(getOrderRequest);
        return orderMapper.toOrderClientResponse(orderResponse);
    }

    public OrderClientResponse updateOrder(UpdateOrderRequest updateOrderRequest) {
        OrderResponse orderResponse = orderServiceBlockingStub.updateOrder(updateOrderRequest);
        return orderMapper.toOrderClientResponse(orderResponse);
    }

    public void deleteOrder(DeleteOrderRequest deleteOrderRequest) {
        try {
            EmptyResponse response = orderServiceBlockingStub.deleteOrder(deleteOrderRequest);
        } catch (Exception e) {
            log.error("Error deleting order: {}", e.getMessage());
            throw e;
        }
    }

}
