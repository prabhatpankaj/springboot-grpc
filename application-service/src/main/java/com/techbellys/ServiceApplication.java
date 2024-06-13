package com.techbellys;

import com.techbellys.serverservice.repository.CategoryRepository;
import com.techbellys.serverservice.repository.ProductRepository;
import com.techbellys.serverservice.service.CategoryService;
import com.techbellys.serverservice.service.OrderService;
import com.techbellys.serverservice.service.ProductService;
import com.techbellys.serverservice.service.impl.CategoryGrpcServiceImpl;
import com.techbellys.serverservice.service.impl.OrderGrpcServiceImpl;
import com.techbellys.serverservice.service.impl.ProductGrpcServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Bean
    public CategoryGrpcServiceImpl categoryGrpcServiceImpl(CategoryService categoryService, CategoryRepository categoryRepository) {
        return new CategoryGrpcServiceImpl(categoryService, categoryRepository);
    }

    @Bean
    public ProductGrpcServiceImpl productGrpcServiceImpl(ProductService productService) {
        return new ProductGrpcServiceImpl(productService);
    }

    @Bean
    public OrderGrpcServiceImpl orderGrpcServiceImpl(OrderService orderService, ProductRepository productRepository) {
        return new OrderGrpcServiceImpl(orderService, productRepository);
    }
}
