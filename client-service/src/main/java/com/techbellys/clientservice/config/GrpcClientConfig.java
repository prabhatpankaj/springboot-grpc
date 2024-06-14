package com.techbellys.clientservice.config;

import com.techbellys.category.CategoryServiceGrpc;
import com.techbellys.messaging.MessagingServiceGrpc;
import com.techbellys.order.OrderServiceGrpc;
import com.techbellys.product.ProductServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @GrpcClient("CategoryService")
    private CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceBlockingStub;

    @GrpcClient("ProductService")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    @GrpcClient("OrderService")
    private OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

    @GrpcClient("MessagingService")
    private MessagingServiceGrpc.MessagingServiceBlockingStub messagingServiceBlockingStub;

    @Bean
    public CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceBlockingStub() {
        return categoryServiceBlockingStub;
    }

    @Bean
    public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub() {
        return productServiceBlockingStub;
    }

    @Bean
    public OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub() {
        return orderServiceBlockingStub;
    }

    @Bean
    public MessagingServiceGrpc.MessagingServiceBlockingStub messagingServiceBlockingStub() {
        return messagingServiceBlockingStub;
    }
}