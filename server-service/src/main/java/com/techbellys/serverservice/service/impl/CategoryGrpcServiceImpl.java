package com.techbellys.serverservice.service.impl;

import com.techbellys.category.*;
import com.techbellys.serverservice.model.Category;
import com.techbellys.serverservice.service.CategoryService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
@Slf4j
@AllArgsConstructor
public class CategoryGrpcServiceImpl extends CategoryServiceGrpc.CategoryServiceImplBase {

    @Autowired
    private CategoryService categoryService;

    @Override
    public void createCategory(CreateCategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {
        Category category = categoryService.createCategory(request.getName());
        CategoryResponse response = CategoryResponse.newBuilder()
                .setCategoryId(category.getId().toString())
                .setName(category.getName())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCategory(GetCategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {
        Category category = categoryService.getCategoryById(Long.parseLong(request.getCategoryId()));
        if (category != null) {
            CategoryResponse response = CategoryResponse.newBuilder()
                    .setCategoryId(category.getId().toString())
                    .setName(category.getName())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new IllegalArgumentException("Category not found"));
        }
    }

    @Override
    public void updateCategory(UpdateCategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {
        Category category = categoryService.updateCategory(Long.parseLong(request.getCategoryId()), request.getName());
        CategoryResponse response = CategoryResponse.newBuilder()
                .setCategoryId(category.getId().toString())
                .setName(category.getName())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCategory(DeleteCategoryRequest request, StreamObserver<EmptyResponse> responseObserver) {
        categoryService.deleteCategory(Long.parseLong(request.getCategoryId()));
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}

