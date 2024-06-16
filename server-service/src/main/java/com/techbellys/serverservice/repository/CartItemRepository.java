package com.techbellys.serverservice.repository;

import com.techbellys.serverservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
