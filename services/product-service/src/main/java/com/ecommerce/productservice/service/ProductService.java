package com.ecommerce.productservice.service;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.model.ProductStatus;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }
    
    public Optional<Product> getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }
    
    public List<Product> getProductsByCategory(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    public Page<Product> getProductsByCategory(String categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }
    
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }
    
    public Page<Product> getProductsByBrand(String brand, Pageable pageable) {
        return productRepository.findByBrand(brand, pageable);
    }
    
    public List<Product> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue();
    }
    
    public List<Product> searchProducts(String searchTerm) {
        return productRepository.findByNameOrDescriptionContaining(searchTerm);
    }
    
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    public Page<Product> getProductsByCategoryAndPriceRange(String categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice, pageable);
    }
    
    public Product createProduct(Product product) {
        if (product.getSku() == null || product.getSku().isEmpty()) {
            product.setSku(generateSku());
        }
        
        if (productRepository.existsBySku(product.getSku())) {
            throw new RuntimeException("Product with SKU " + product.getSku() + " already exists");
        }
        
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        
        return productRepository.save(product);
    }
    
    public Product updateProduct(String id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        
        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        
        Product existing = existingProduct.get();
        
        // Check if SKU is being changed and if new SKU already exists
        if (!existing.getSku().equals(product.getSku()) && productRepository.existsBySku(product.getSku())) {
            throw new RuntimeException("Product with SKU " + product.getSku() + " already exists");
        }
        
        product.setId(id);
        product.setCreatedAt(existing.getCreatedAt());
        product.setUpdatedAt(LocalDateTime.now());
        
        return productRepository.save(product);
    }
    
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    public Product updateProductStatus(String id, ProductStatus status) {
        Optional<Product> productOpt = productRepository.findById(id);
        
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        
        Product product = productOpt.get();
        product.setStatus(status);
        product.setUpdatedAt(LocalDateTime.now());
        
        return productRepository.save(product);
    }
    
    public Product toggleFeaturedStatus(String id) {
        Optional<Product> productOpt = productRepository.findById(id);
        
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        
        Product product = productOpt.get();
        product.setFeatured(!product.isFeatured());
        product.setUpdatedAt(LocalDateTime.now());
        
        return productRepository.save(product);
    }
    
    private String generateSku() {
        return "PRD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public boolean existsById(String id) {
        return productRepository.existsById(id);
    }
    
    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }
}