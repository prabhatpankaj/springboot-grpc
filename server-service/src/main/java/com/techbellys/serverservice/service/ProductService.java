package com.techbellys.serverservice.service;

import com.techbellys.serverservice.model.Product;

public interface ProductService {
    Product createProduct(String name, float price, Long categoryId);

    Product getProductById(Long id);

    Product updateProduct(Long id, String name, float price, Long categoryId);

    void deleteProduct(Long id);
}

