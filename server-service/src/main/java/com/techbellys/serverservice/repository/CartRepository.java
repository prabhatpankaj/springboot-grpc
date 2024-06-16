package com.techbellys.serverservice.repository;
import com.techbellys.serverservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
