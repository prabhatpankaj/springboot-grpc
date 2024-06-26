package com.techbellys.serverservice.service;

import com.techbellys.serverservice.model.Category;

public interface CategoryServerService {
    Category createCategory(String name);
    Category getCategoryById(Long id);
    Category updateCategory(Long id, String name);
    void deleteCategory(Long id);
}
