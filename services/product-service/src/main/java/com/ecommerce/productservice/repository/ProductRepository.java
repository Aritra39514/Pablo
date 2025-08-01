package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.model.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    
    Optional<Product> findBySku(String sku);
    
    List<Product> findByCategoryId(String categoryId);
    
    List<Product> findByBrand(String brand);
    
    List<Product> findByStatus(ProductStatus status);
    
    List<Product> findByFeaturedTrue();
    
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);
    
    Page<Product> findByBrand(String brand, Pageable pageable);
    
    Page<Product> findByStatus(ProductStatus status, Pageable pageable);
    
    @Query("{ 'price' : { '$gte' : ?0, '$lte' : ?1 } }")
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("{ '$text': { '$search': ?0 } }")
    List<Product> findByNameOrDescriptionContaining(String searchTerm);
    
    @Query("{ '$and': [ { 'categoryId': ?0 }, { 'price': { '$gte': ?1, '$lte': ?2 } } ] }")
    Page<Product> findByCategoryIdAndPriceBetween(String categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    boolean existsBySku(String sku);
}