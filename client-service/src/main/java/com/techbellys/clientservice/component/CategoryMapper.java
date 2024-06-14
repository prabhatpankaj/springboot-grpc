package com.techbellys.clientservice.component;

import com.techbellys.category.*;
import com.techbellys.clientservice.dto.CategoryClientRequest;
import com.techbellys.clientservice.dto.CategoryClientResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CreateCategoryRequest toCreateCategoryRequest(CategoryClientRequest categoryClientRequest) {
        return CreateCategoryRequest.newBuilder()
                .setName(categoryClientRequest.getName())
                .build();
    }

    public UpdateCategoryRequest toUpdateCategoryRequest(String id, CategoryClientRequest categoryClientRequest) {
        return UpdateCategoryRequest.newBuilder()
                .setCategoryId(id)
                .setName(categoryClientRequest.getName())
                .build();
    }

    public CategoryClientResponse toCategoryClientResponse(CategoryResponse categoryResponse) {
        return CategoryClientResponse.builder()
                .categoryId(categoryResponse.getCategoryId())
                .name(categoryResponse.getName())
                .build();
    }

    public ListCategoriesRequest toListCategoriesRequest() {
        return ListCategoriesRequest.newBuilder().build();
    }
}
