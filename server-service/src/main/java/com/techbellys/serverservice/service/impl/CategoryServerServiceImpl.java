package com.techbellys.serverservice.service.impl;

import com.techbellys.serverservice.model.Category;
import com.techbellys.serverservice.repository.CategoryRepository;
import com.techbellys.serverservice.service.CategoryServerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServerServiceImpl implements CategoryServerService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(String name) {
        Category category = Category.builder().name(name).build();
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category updateCategory(Long id, String name) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
