package com.noisy.rrssProject.controller;

import java.util.List;

import com.noisy.rrssProject.model.dto.request.CreateCategoryRequest;
import com.noisy.rrssProject.model.dto.response.CategoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.noisy.rrssProject.model.entity.Category;
import com.noisy.rrssProject.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String name) {
        Category optionalCategory = categoryService.getCategoryByName(name);

        if (optionalCategory == null) {
            return ResponseEntity.notFound().build();
        }

        String parentCategoryName = optionalCategory.getParentCategory() != null ? optionalCategory.getParentCategory().getName() : null;
        CategoryResponse categoryResponse = new CategoryResponse(optionalCategory.getName(), parentCategoryName);

        return ResponseEntity.ok(categoryResponse);
    }


    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequest request) {
        Category createdCategory = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CreateCategoryRequest request) {
        Category updatedCategory = categoryService.updateCategoryById(id, request);
        return ResponseEntity.ok(updatedCategory);

    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteCategory(@PathVariable String name) {
        categoryService.deleteCategory(name);
        return ResponseEntity.ok("Category deleted successfully.");
    }
}
