package com.techbellys.clientservice.service;

import com.techbellys.clientservice.component.CategoryMapper;
import com.techbellys.clientservice.dto.CategoryClientRequest;
import com.techbellys.clientservice.dto.CategoryClientResponse;
import com.techbellys.category.*;
import com.techbellys.product.DeleteProductRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceBlockingStub;
    private final CategoryMapper categoryMapper;

    public CategoryClientResponse createCategory(CategoryClientRequest categoryClientRequest) {
        CreateCategoryRequest serverRequest = categoryMapper.toServerRequest(categoryClientRequest);
        CategoryResponse serverResponse = categoryServiceBlockingStub.createCategory(serverRequest);
        return categoryMapper.toClientResponse(serverResponse);
    }

    public CategoryClientResponse getCategory(String id) {
        GetCategoryRequest serverRequest = GetCategoryRequest.newBuilder().setCategoryId(id).build();
        CategoryResponse serverResponse = categoryServiceBlockingStub.getCategory(serverRequest);
        return categoryMapper.toClientResponse(serverResponse);
    }

    public CategoryClientResponse updateCategory(String id, CategoryClientRequest categoryClientRequest) {
        UpdateCategoryRequest serverRequest = categoryMapper.toServerUpdateRequest(id, categoryClientRequest);
        CategoryResponse serverResponse = categoryServiceBlockingStub.updateCategory(serverRequest);
        return categoryMapper.toClientResponse(serverResponse);
    }

    public void deleteCategory(String id) {
        try {
            DeleteCategoryRequest serverRequest = DeleteCategoryRequest.newBuilder().setCategoryId(id).build();
            EmptyResponse response = categoryServiceBlockingStub.deleteCategory(serverRequest);
        } catch (Exception e) {
            log.error("Error deleting delete with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
