package com.techbellys.clientservice.controller;

import com.techbellys.clientservice.dto.OrderClientResponse;
import com.techbellys.clientservice.service.OrderService;
import com.techbellys.order.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderClientResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        OrderClientResponse response = orderService.createOrder(createOrderRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderClientResponse> getOrder(@PathVariable String orderId) {
        GetOrderRequest request = GetOrderRequest.newBuilder().setOrderId(orderId).build();
        OrderClientResponse response = orderService.getOrder(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderClientResponse> updateOrder(@PathVariable String orderId, @RequestBody UpdateOrderRequest updateOrderRequest) {
        updateOrderRequest = updateOrderRequest.toBuilder().setOrderId(orderId).build();
        OrderClientResponse response = orderService.updateOrder(updateOrderRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        DeleteOrderRequest request = DeleteOrderRequest.newBuilder().setOrderId(orderId).build();
        orderService.deleteOrder(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
