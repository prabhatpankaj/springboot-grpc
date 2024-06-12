package com.techbellys.serverservice.repository;
import com.techbellys.serverservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
