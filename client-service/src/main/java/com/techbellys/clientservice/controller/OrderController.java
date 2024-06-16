package com.techbellys.clientservice.controller;

import com.techbellys.clientservice.dto.OrderClientResponse;
import com.techbellys.clientservice.service.OrderClientService;
import com.techbellys.order.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final OrderClientService orderClientService;

    @PreAuthorize("hasAnyRole('client')")
    @PostMapping
    public ResponseEntity<OrderClientResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest, Authentication auth) {
        OrderClientResponse response = orderClientService.createOrder(createOrderRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderClientResponse> getOrder(@PathVariable String orderId) {
        GetOrderRequest request = GetOrderRequest.newBuilder().setOrderId(orderId).build();
        OrderClientResponse response = orderClientService.getOrder(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderClientResponse> updateOrder(@PathVariable String orderId, @RequestBody UpdateOrderRequest updateOrderRequest) {
        updateOrderRequest = updateOrderRequest.toBuilder().setOrderId(orderId).build();
        OrderClientResponse response = orderClientService.updateOrder(updateOrderRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        DeleteOrderRequest request = DeleteOrderRequest.newBuilder().setOrderId(orderId).build();
        orderClientService.deleteOrder(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
