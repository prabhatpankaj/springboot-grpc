package com.techbellys.clientservice.component;

import com.techbellys.clientservice.dto.CategoryClientRequest;
import com.techbellys.clientservice.dto.CategoryClientResponse;
import com.techbellys.category.*;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CreateCategoryRequest toServerRequest(CategoryClientRequest categoryClientRequest) {
        return CreateCategoryRequest.newBuilder()
                .setName(categoryClientRequest.getName())
                .build();
    }

    public UpdateCategoryRequest toServerUpdateRequest(String id, CategoryClientRequest categoryClientRequest) {
        return UpdateCategoryRequest.newBuilder()
                .setCategoryId(id)
                .setName(categoryClientRequest.getName())
                .build();
    }

    public CategoryClientResponse toClientResponse(CategoryResponse categoryResponse) {
        return CategoryClientResponse.builder()
                .categoryId(categoryResponse.getCategoryId())
                .name(categoryResponse.getName())
                .build();
    }
}
