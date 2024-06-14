package com.techbellys.clientservice.service;

import com.techbellys.clientservice.component.ProductMapper;
import com.techbellys.clientservice.dto.ProductClientRequest;
import com.techbellys.clientservice.dto.ProductClientResponse;
import com.techbellys.product.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    @GrpcClient("ProductService")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    private final ProductMapper productMapper;

    public ProductClientResponse createProduct(ProductClientRequest productClientRequest) {
        CreateProductRequest serverRequest = productMapper.toServerRequest(productClientRequest);
        ProductResponse serverResponse = productServiceBlockingStub.createProduct(serverRequest);
        return productMapper.toClientResponse(serverResponse);
    }

    public ProductClientResponse getProduct(String id) {
        GetProductRequest serverRequest = GetProductRequest.newBuilder().setProductId(id).build();
        ProductResponse serverResponse = productServiceBlockingStub.getProduct(serverRequest);
        return productMapper.toClientResponse(serverResponse);
    }

    public ProductClientResponse updateProduct(String id, ProductClientRequest productClientRequest) {
        UpdateProductRequest serverRequest = productMapper.toServerUpdateRequest(id, productClientRequest);
        ProductResponse serverResponse = productServiceBlockingStub.updateProduct(serverRequest);
        return productMapper.toClientResponse(serverResponse);
    }
    public void deleteProduct(String id) {
        try {
            DeleteProductRequest serverRequest = DeleteProductRequest.newBuilder().setProductId(id).build();
            EmptyResponse response = productServiceBlockingStub.deleteProduct(serverRequest);
        } catch (Exception e) {
            log.error("Error deleting product with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
