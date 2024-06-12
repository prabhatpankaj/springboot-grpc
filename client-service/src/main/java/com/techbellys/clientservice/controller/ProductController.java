package com.techbellys.clientservice.controller;

import com.techbellys.clientservice.dto.ProductClientRequest;
import com.techbellys.clientservice.dto.ProductClientResponse;
import com.techbellys.clientservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductClientResponse> createProduct(@RequestBody ProductClientRequest productClientRequest) {
        return new ResponseEntity<>(productService.createProduct(productClientRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductClientResponse> getProduct(@PathVariable String id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductClientResponse> updateProduct(@PathVariable String id, @RequestBody ProductClientRequest productClientRequest) {
        return new ResponseEntity<>(productService.updateProduct(id, productClientRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}