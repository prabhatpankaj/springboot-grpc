package com.techbellys.clientservice.component;

import com.techbellys.clientservice.dto.ProductClientRequest;
import com.techbellys.clientservice.dto.ProductClientResponse;
import com.techbellys.product.*;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public CreateProductRequest toServerRequest(ProductClientRequest productClientRequest) {
        return CreateProductRequest.newBuilder()
                .setName(productClientRequest.getName())
                .setPrice((float) productClientRequest.getPrice())
                .setCategoryId(productClientRequest.getCategoryId())
                .build();
    }

    public UpdateProductRequest toServerUpdateRequest(String id, ProductClientRequest productClientRequest) {
        return UpdateProductRequest.newBuilder()
                .setProductId(id)
                .setName(productClientRequest.getName())
                .setPrice((float) productClientRequest.getPrice())
                .setCategoryId(productClientRequest.getCategoryId())
                .build();
    }

    public ProductClientResponse toClientResponse(ProductResponse productResponse) {
        return ProductClientResponse.builder()
                .productId(productResponse.getProductId())
                .name(productResponse.getName())
                .price(productResponse.getPrice())
                .categoryId(productResponse.getCategoryId())
                .build();
    }
}
