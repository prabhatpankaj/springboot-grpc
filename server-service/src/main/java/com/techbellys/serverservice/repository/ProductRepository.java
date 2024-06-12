package com.techbellys.serverservice.repository;
import com.techbellys.serverservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
