package com.ecommerce.productservice.model;

public class ProductAttribute {
    private String name;
    private String value;
    private String type; // TEXT, NUMBER, BOOLEAN, COLOR, etc.
    
    public ProductAttribute() {}
    
    public ProductAttribute(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}