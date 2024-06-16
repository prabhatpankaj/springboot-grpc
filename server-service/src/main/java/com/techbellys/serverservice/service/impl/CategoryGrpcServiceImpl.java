package com.techbellys.serverservice.service.impl;

import com.techbellys.category.*;
import com.techbellys.serverservice.model.Category;
import com.techbellys.serverservice.repository.CategoryRepository;
import com.techbellys.serverservice.service.CategoryServerService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class CategoryGrpcServiceImpl extends CategoryServiceGrpc.CategoryServiceImplBase {

    private final CategoryServerService categoryServerService;
    private final CategoryRepository categoryRepository;

    @Override
    public void listCategories(ListCategoriesRequest request, StreamObserver<ListCategoriesResponse> responseObserver) {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryResponse> categoryResponses = categories.stream()
                    .map(category -> CategoryResponse.newBuilder()
                            .setCategoryId(category.getId().toString())
                            .setName(category.getName())
                            .build())
                    .collect(Collectors.toList());

            ListCategoriesResponse response = ListCategoriesResponse.newBuilder()
                    .addAllCategories(categoryResponses)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void createCategory(CreateCategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {
        Category category = categoryServerService.createCategory(request.getName());
        CategoryResponse response = CategoryResponse.newBuilder()
                .setCategoryId(category.getId().toString())
                .setName(category.getName())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCategory(GetCategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {
        Category category = categoryServerService.getCategoryById(Long.parseLong(request.getCategoryId()));
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
        Category category = categoryServerService.updateCategory(Long.parseLong(request.getCategoryId()), request.getName());
        CategoryResponse response = CategoryResponse.newBuilder()
                .setCategoryId(category.getId().toString())
                .setName(category.getName())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCategory(DeleteCategoryRequest request, StreamObserver<EmptyResponse> responseObserver) {
        categoryServerService.deleteCategory(Long.parseLong(request.getCategoryId()));
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}
