package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    
    Optional<Category> findByName(String name);
    
    List<Category> findByParentId(String parentId);
    
    List<Category> findByActiveTrue();
    
    List<Category> findByParentIdIsNull(); // Root categories
    
    boolean existsByName(String name);
}