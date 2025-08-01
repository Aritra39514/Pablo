#!/bin/bash

echo "🚀 Starting Ecommerce Platform..."

echo "📦 Building and starting all services with Docker Compose..."
docker-compose up --build -d

echo "⏳ Waiting for services to be ready..."
sleep 30

echo "🔍 Checking service health..."

# Check infrastructure services
echo "Checking MongoDB..."
docker-compose exec mongodb mongosh --eval "db.runCommand('ping')" || echo "MongoDB not ready"

echo "Checking Redis..."
docker-compose exec redis redis-cli ping || echo "Redis not ready"

echo "Checking Kafka..."
docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --list || echo "Kafka not ready"

# Check application services
echo "Checking User Service..."
curl -f http://localhost:8081/api/users/health || echo "User Service not ready"

echo "Checking Product Service..."
curl -f http://localhost:8082/api/products/health || echo "Product Service not ready"

echo "Checking Order Service..."
curl -f http://localhost:8083/api/orders/health || echo "Order Service not ready"

echo "Checking Inventory Service..."
curl -f http://localhost:8084/health || echo "Inventory Service not ready"

echo "Checking API Gateway..."
curl -f http://localhost:8080/actuator/health || echo "API Gateway not ready"

echo "Checking Frontend..."
curl -f http://localhost:4200 || echo "Frontend not ready"

echo "🎉 Ecommerce Platform startup complete!"
echo ""
echo "📋 Service URLs:"
echo "   🌐 Frontend:           http://localhost:4200"
echo "   🚪 API Gateway:        http://localhost:8080"
echo "   👤 User Service:       http://localhost:8081"
echo "   📦 Product Service:    http://localhost:8082"
echo "   🛒 Order Service:      http://localhost:8083"
echo "   📊 Inventory Service:  http://localhost:8084"
echo "   📈 Grafana:           http://localhost:3000 (admin/admin)"
echo "   🔥 Prometheus:        http://localhost:9090"
echo ""
echo "🧪 Sample API Calls:"
echo "   # Register a user"
echo "   curl -X POST http://localhost:8081/api/users/register \\"
echo "     -H 'Content-Type: application/json' \\"
echo "     -d '{\"username\":\"john\",\"email\":\"john@example.com\",\"password\":\"password\",\"firstName\":\"John\",\"lastName\":\"Doe\"}'"
echo ""
echo "   # Login"
echo "   curl -X POST http://localhost:8081/api/users/login \\"
echo "     -H 'Content-Type: application/json' \\"
echo "     -d '{\"username\":\"john\",\"password\":\"password\"}'"
echo ""
echo "   # Get products"
echo "   curl http://localhost:8082/api/products"
echo ""
echo "📚 For complete API documentation, visit the service endpoints with /docs (e.g., http://localhost:8081/docs)"