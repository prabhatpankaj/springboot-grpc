package com.techbellys.clientservice.controller;

import com.techbellys.clientservice.dto.CategoryClientRequest;
import com.techbellys.clientservice.dto.CategoryClientResponse;
import com.techbellys.clientservice.service.CategoryClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryClientService categoryClientService;

    @GetMapping
    public ResponseEntity<List<CategoryClientResponse>> listCategories() {
        return new ResponseEntity<>(categoryClientService.listCategories(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CategoryClientResponse> createCategory(@RequestBody CategoryClientRequest categoryClientRequest) {
        return new ResponseEntity<>(categoryClientService.createCategory(categoryClientRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryClientResponse> getCategory(@PathVariable String id) {
        return new ResponseEntity<>(categoryClientService.getCategory(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryClientResponse> updateCategory(@PathVariable String id, @RequestBody CategoryClientRequest categoryClientRequest) {
        return new ResponseEntity<>(categoryClientService.updateCategory(id, categoryClientRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryClientService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
