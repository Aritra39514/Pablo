package com.ecommerce.productservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Document(collection = "products")
public class Product {
    
    @Id
    private String id;
    
    @TextIndexed
    private String name;
    
    @TextIndexed
    private String description;
    
    @Indexed
    private String sku;
    
    private BigDecimal price;
    
    @Indexed
    private String categoryId;
    
    private String categoryName;
    
    @Indexed
    private String brand;
    
    private List<String> images;
    
    private List<ProductAttribute> attributes;
    
    private ProductDimensions dimensions;
    
    private Double weight;
    
    @Indexed
    private ProductStatus status;
    
    private boolean featured;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    public Product() {
        this.status = ProductStatus.ACTIVE;
        this.featured = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSku() {
        return sku;
    }
    
    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public List<String> getImages() {
        return images;
    }
    
    public void setImages(List<String> images) {
        this.images = images;
    }
    
    public List<ProductAttribute> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }
    
    public ProductDimensions getDimensions() {
        return dimensions;
    }
    
    public void setDimensions(ProductDimensions dimensions) {
        this.dimensions = dimensions;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public ProductStatus getStatus() {
        return status;
    }
    
    public void setStatus(ProductStatus status) {
        this.status = status;
    }
    
    public boolean isFeatured() {
        return featured;
    }
    
    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}