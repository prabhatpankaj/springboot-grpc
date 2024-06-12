package com.techbellys.serverservice.service.impl;

import com.techbellys.product.*;
import com.techbellys.serverservice.model.Product;
import com.techbellys.serverservice.service.ProductService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
@Slf4j
@AllArgsConstructor
class ProductGrpcServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private ProductService productService;

    @Override
    public void createProduct(CreateProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        try {
            Product product = productService.createProduct(request.getName(), request.getPrice(), Long.parseLong(request.getCategoryId()));
            ProductResponse response = ProductResponse.newBuilder()
                    .setProductId(product.getId().toString())
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .setCategoryId(product.getCategory().getId().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        Product product = productService.getProductById(Long.parseLong(request.getProductId()));
        if (product != null) {
            ProductResponse response = ProductResponse.newBuilder()
                    .setProductId(product.getId().toString())
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .setCategoryId(product.getCategory().getId().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new IllegalArgumentException("Product not found"));
        }
    }

    @Override
    public void updateProduct(UpdateProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        try {
            Product product = productService.updateProduct(Long.parseLong(request.getProductId()), request.getName(), request.getPrice(), Long.parseLong(request.getCategoryId()));
            ProductResponse response = ProductResponse.newBuilder()
                    .setProductId(product.getId().toString())
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .setCategoryId(product.getCategory().getId().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void deleteProduct(DeleteProductRequest request, StreamObserver<EmptyResponse> responseObserver) {
        productService.deleteProduct(Long.parseLong(request.getProductId()));
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}