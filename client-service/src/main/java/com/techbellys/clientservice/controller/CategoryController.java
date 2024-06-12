package com.techbellys.clientservice.controller;

import com.techbellys.clientservice.dto.CategoryClientRequest;
import com.techbellys.clientservice.dto.CategoryClientResponse;
import com.techbellys.clientservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryClientResponse>> listCategories() {
        return new ResponseEntity<>(categoryService.listCategories(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CategoryClientResponse> createCategory(@RequestBody CategoryClientRequest categoryClientRequest) {
        return new ResponseEntity<>(categoryService.createCategory(categoryClientRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryClientResponse> getCategory(@PathVariable String id) {
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryClientResponse> updateCategory(@PathVariable String id, @RequestBody CategoryClientRequest categoryClientRequest) {
        return new ResponseEntity<>(categoryService.updateCategory(id, categoryClientRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
