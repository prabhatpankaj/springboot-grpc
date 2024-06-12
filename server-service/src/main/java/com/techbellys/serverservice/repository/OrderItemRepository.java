package com.techbellys.serverservice.repository;
import com.techbellys.serverservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
