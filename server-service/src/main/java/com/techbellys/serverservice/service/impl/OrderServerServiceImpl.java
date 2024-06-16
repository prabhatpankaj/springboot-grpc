package com.techbellys.serverservice.service.impl;

import com.techbellys.serverservice.model.Order;
import com.techbellys.serverservice.model.OrderItem;
import com.techbellys.serverservice.repository.OrderRepository;
import com.techbellys.serverservice.repository.ProductRepository;
import com.techbellys.serverservice.service.OrderServerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServerServiceImpl implements OrderServerService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order createOrder(List<OrderItem> items) {
        Order order = new Order();
        order.setItems(items);
        items.forEach(item -> item.setOrder(order));
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
