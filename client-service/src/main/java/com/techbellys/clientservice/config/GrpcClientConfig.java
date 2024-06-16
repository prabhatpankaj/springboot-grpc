package com.techbellys.clientservice.config;

import com.techbellys.category.CategoryServiceGrpc;
import com.techbellys.order.OrderServiceGrpc;
import com.techbellys.payment.PaymentServiceGrpc;
import com.techbellys.product.ProductServiceGrpc;
import com.techbellys.cart.CartServiceGrpc;
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

    @GrpcClient("PaymentService")
    private PaymentServiceGrpc.PaymentServiceBlockingStub paymentServiceBlockingStub;

    @GrpcClient("CartService")
    private CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub;

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
    public PaymentServiceGrpc.PaymentServiceBlockingStub paymentServiceBlockingStub() {
        return paymentServiceBlockingStub;
    }
    @Bean
    public CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub() {
        return cartServiceBlockingStub;
    }
}