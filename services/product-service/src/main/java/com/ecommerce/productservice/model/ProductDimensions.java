package com.ecommerce.productservice.model;

public class ProductDimensions {
    private Double length;
    private Double width;
    private Double height;
    private String unit; // cm, inch, etc.
    
    public ProductDimensions() {}
    
    public ProductDimensions(Double length, Double width, Double height, String unit) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.unit = unit;
    }
    
    // Getters and Setters
    public Double getLength() {
        return length;
    }
    
    public void setLength(Double length) {
        this.length = length;
    }
    
    public Double getWidth() {
        return width;
    }
    
    public void setWidth(Double width) {
        this.width = width;
    }
    
    public Double getHeight() {
        return height;
    }
    
    public void setHeight(Double height) {
        this.height = height;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
}