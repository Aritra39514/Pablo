package com.ecommerce.productservice.service;

import com.ecommerce.productservice.model.Category;
import com.ecommerce.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public List<Category> getActiveCategories() {
        return categoryRepository.findByActiveTrue();
    }
    
    public List<Category> getRootCategories() {
        return categoryRepository.findByParentIdIsNull();
    }
    
    public List<Category> getSubCategories(String parentId) {
        return categoryRepository.findByParentId(parentId);
    }
    
    public Optional<Category> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }
    
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
    
    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category with name '" + category.getName() + "' already exists");
        }
        
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        
        return categoryRepository.save(category);
    }
    
    public Category updateCategory(String id, Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        
        if (existingCategory.isEmpty()) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        
        Category existing = existingCategory.get();
        
        // Check if name is being changed and if new name already exists
        if (!existing.getName().equals(category.getName()) && categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category with name '" + category.getName() + "' already exists");
        }
        
        category.setId(id);
        category.setCreatedAt(existing.getCreatedAt());
        category.setUpdatedAt(LocalDateTime.now());
        
        return categoryRepository.save(category);
    }
    
    public void deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        
        // Check if category has subcategories
        List<Category> subCategories = categoryRepository.findByParentId(id);
        if (!subCategories.isEmpty()) {
            throw new RuntimeException("Cannot delete category with subcategories. Please delete subcategories first.");
        }
        
        categoryRepository.deleteById(id);
    }
    
    public Category toggleActiveStatus(String id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        
        Category category = categoryOpt.get();
        category.setActive(!category.isActive());
        category.setUpdatedAt(LocalDateTime.now());
        
        return categoryRepository.save(category);
    }
    
    public boolean existsById(String id) {
        return categoryRepository.existsById(id);
    }
    
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}