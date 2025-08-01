#!/bin/bash

echo "🏗️ Initializing sample data for Ecommerce Platform..."

# Wait for services to be ready
sleep 60

API_BASE="http://localhost:8080"

echo "👤 Creating sample users..."

# Create admin user
curl -X POST "${API_BASE}/api/users/register" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@ecommerce.com",
    "password": "admin123",
    "firstName": "Admin",
    "lastName": "User"
  }'

# Create regular user
curl -X POST "${API_BASE}/api/users/register" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "email": "john@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'

echo "📂 Creating product categories..."

# Create Electronics category
curl -X POST "${API_BASE}/api/categories" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Electronics",
    "description": "Electronic devices and gadgets",
    "active": true
  }'

# Create Clothing category
curl -X POST "${API_BASE}/api/categories" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Clothing",
    "description": "Fashion and apparel",
    "active": true
  }'

# Create Books category
curl -X POST "${API_BASE}/api/categories" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Books",
    "description": "Books and literature",
    "active": true
  }'

echo "📦 Creating sample products..."

# Get category IDs (this would need to be done programmatically in a real scenario)
# For now, we'll create products without specific category IDs

# Create sample products
curl -X POST "${API_BASE}/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Pro 15",
    "description": "High-performance laptop with 16GB RAM and 512GB SSD",
    "sku": "LP15-001",
    "price": 1299.99,
    "categoryName": "Electronics",
    "brand": "TechBrand",
    "images": ["laptop1.jpg", "laptop2.jpg"],
    "featured": true,
    "attributes": [
      {"name": "RAM", "value": "16GB", "type": "TEXT"},
      {"name": "Storage", "value": "512GB SSD", "type": "TEXT"},
      {"name": "Screen Size", "value": "15 inch", "type": "TEXT"}
    ]
  }'

curl -X POST "${API_BASE}/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Wireless Headphones",
    "description": "Premium noise-cancelling wireless headphones",
    "sku": "WH-001",
    "price": 299.99,
    "categoryName": "Electronics",
    "brand": "AudioTech",
    "images": ["headphones1.jpg"],
    "featured": true,
    "attributes": [
      {"name": "Battery Life", "value": "30 hours", "type": "TEXT"},
      {"name": "Noise Cancelling", "value": "Yes", "type": "BOOLEAN"},
      {"name": "Color", "value": "Black", "type": "COLOR"}
    ]
  }'

curl -X POST "${API_BASE}/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Cotton T-Shirt",
    "description": "Comfortable 100% cotton t-shirt",
    "sku": "TS-001",
    "price": 29.99,
    "categoryName": "Clothing",
    "brand": "FashionCo",
    "images": ["tshirt1.jpg"],
    "featured": false,
    "attributes": [
      {"name": "Material", "value": "100% Cotton", "type": "TEXT"},
      {"name": "Size", "value": "M", "type": "TEXT"},
      {"name": "Color", "value": "Blue", "type": "COLOR"}
    ]
  }'

curl -X POST "${API_BASE}/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Programming Book",
    "description": "Learn modern web development",
    "sku": "BOOK-001",
    "price": 49.99,
    "categoryName": "Books",
    "brand": "TechPublisher",
    "images": ["book1.jpg"],
    "featured": false,
    "attributes": [
      {"name": "Author", "value": "John Smith", "type": "TEXT"},
      {"name": "Pages", "value": "400", "type": "NUMBER"},
      {"name": "Format", "value": "Paperback", "type": "TEXT"}
    ]
  }'

curl -X POST "${API_BASE}/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone X",
    "description": "Latest flagship smartphone with advanced camera",
    "sku": "SPX-001",
    "price": 899.99,
    "categoryName": "Electronics",
    "brand": "PhoneTech",
    "images": ["phone1.jpg", "phone2.jpg"],
    "featured": true,
    "attributes": [
      {"name": "Storage", "value": "128GB", "type": "TEXT"},
      {"name": "Camera", "value": "48MP", "type": "TEXT"},
      {"name": "Screen Size", "value": "6.1 inch", "type": "TEXT"}
    ]
  }'

echo "✅ Sample data initialization complete!"
echo ""
echo "🎯 You can now:"
echo "   • Browse products at http://localhost:4200"
echo "   • Login with username: john, password: password123"
echo "   • Or create a new account"
echo "   • View admin features with username: admin, password: admin123"