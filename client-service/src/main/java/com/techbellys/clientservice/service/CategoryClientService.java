package com.techbellys.clientservice.service;

import com.techbellys.clientservice.component.CategoryMapper;
import com.techbellys.clientservice.dto.CategoryClientRequest;
import com.techbellys.clientservice.dto.CategoryClientResponse;
import com.techbellys.category.*;
import com.techbellys.category.EmptyResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryClientService {

    @GrpcClient("CategoryService")
    private CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceBlockingStub;

    private final CategoryMapper categoryMapper;

    public CategoryClientResponse createCategory(CategoryClientRequest categoryClientRequest) {
        CreateCategoryRequest request = categoryMapper.toCreateCategoryRequest(categoryClientRequest);
        CategoryResponse response = categoryServiceBlockingStub.createCategory(request);
        return categoryMapper.toCategoryClientResponse(response);
    }

    public CategoryClientResponse getCategory(String id) {
        GetCategoryRequest request = GetCategoryRequest.newBuilder().setCategoryId(id).build();
        CategoryResponse response = categoryServiceBlockingStub.getCategory(request);
        return categoryMapper.toCategoryClientResponse(response);
    }

    public CategoryClientResponse updateCategory(String id, CategoryClientRequest categoryClientRequest) {
        UpdateCategoryRequest request = categoryMapper.toUpdateCategoryRequest(id, categoryClientRequest);
        CategoryResponse response = categoryServiceBlockingStub.updateCategory(request);
        return categoryMapper.toCategoryClientResponse(response);
    }

    public void deleteCategory(String id) {
        try {
            DeleteCategoryRequest request = DeleteCategoryRequest.newBuilder().setCategoryId(id).build();
            EmptyResponse response = categoryServiceBlockingStub.deleteCategory(request);
        } catch (Exception e) {
            log.error("Error deleting product with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public List<CategoryClientResponse> listCategories() {
        ListCategoriesRequest request = ListCategoriesRequest.newBuilder().build();
        ListCategoriesResponse response = categoryServiceBlockingStub.listCategories(request);
        return response.getCategoriesList().stream()
                .map(categoryMapper::toCategoryClientResponse)
                .collect(Collectors.toList());
    }
}
