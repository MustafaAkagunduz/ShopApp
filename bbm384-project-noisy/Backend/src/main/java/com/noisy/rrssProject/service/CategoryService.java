package com.noisy.rrssProject.service;

import com.noisy.rrssProject.model.dto.request.CreateCategoryRequest;
import com.noisy.rrssProject.model.dto.response.CategoryResponse;
import com.noisy.rrssProject.repository.CategoryRepository;
import com.noisy.rrssProject.model.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    //DTO sınıfları kasten yazılmadı. Çünkü gerek yok.

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    String parentCategoryName = category.getParentCategory() != null ? category.getParentCategory().getName() : null;
                    return new CategoryResponse(category.getName(), parentCategoryName);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Category createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        if (request.parentName() != null) {
            Category parentCategory = categoryRepository.findByName(request.parentName()).orElse(
                    new Category(null,request.parentName(),null)
            );
            category.setParentCategory(parentCategory);
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(String name) {
        categoryRepository.deleteByName(name);
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow();
    }

    @Transactional
    public Category updateCategoryById(Long id, CreateCategoryRequest request){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(request.name());

            if (request.parentName() != null) {
                Category parentCategory = categoryRepository.findByName(request.parentName()).
                        orElse(new Category(null,request.parentName(),null));
                category.setParentCategory(parentCategory);
            }

            return categoryRepository.save(category);
        } else {
            throw new RuntimeException("Category not found with id: " + id);
        }
    }
}
