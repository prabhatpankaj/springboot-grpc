package com.techbellys.serverservice.service;

import com.techbellys.serverservice.model.Order;
import com.techbellys.serverservice.model.OrderItem;

import java.util.List;

public interface OrderServerService {
    Order createOrder(List<OrderItem> items);
    Order getOrderById(Long id);
    void updateOrder(Order order);
    void deleteOrder(Long id);
}