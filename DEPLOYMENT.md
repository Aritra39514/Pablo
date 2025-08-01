# Ecommerce Platform - Deployment Guide

## Quick Start

### Prerequisites
- Docker and Docker Compose installed
- At least 8GB RAM available
- Ports 3000, 4200, 6379, 8080-8084, 9090, 9092, 27017 available

### 1. Clone and Start
```bash
# Make startup script executable (if not already)
chmod +x start.sh

# Start the entire platform
./start.sh
```

### 2. Initialize Sample Data
```bash
# Wait for all services to start (about 2-3 minutes), then:
./init-data.sh
```

### 3. Access the Platform
- **Frontend**: http://localhost:4200
- **API Gateway**: http://localhost:8080
- **Grafana Dashboard**: http://localhost:3000 (admin/admin)

## Architecture Overview

### Microservices
1. **API Gateway** (Spring Cloud Gateway) - Port 8080
2. **User Service** (Spring Boot + MongoDB + Redis) - Port 8081
3. **Product Service** (Spring Boot + MongoDB) - Port 8082
4. **Order Service** (Spring Boot + MongoDB + Kafka) - Port 8083
5. **Inventory Service** (Python FastAPI + MongoDB + gRPC) - Port 8084
6. **Notification Service** (Python + Kafka) - Background service
7. **Frontend** (Angular + TypeScript) - Port 4200

### Infrastructure
- **MongoDB** - Port 27017 (Database)
- **Redis** - Port 6379 (Caching)
- **Apache Kafka** - Port 9092 (Message Queue)
- **Zookeeper** - Port 2181 (Kafka coordination)
- **Prometheus** - Port 9090 (Metrics collection)
- **Grafana** - Port 3000 (Monitoring dashboard)

## Service Details

### User Service
- **Purpose**: User authentication, authorization, profile management
- **Features**: JWT authentication, Redis caching, user registration/login
- **Database**: MongoDB collection `users`
- **Cache**: Redis for session management

### Product Service
- **Purpose**: Product catalog management, categories
- **Features**: Product CRUD, category management, search, filtering
- **Database**: MongoDB collections `products`, `categories`

### Order Service
- **Purpose**: Order processing, payment handling
- **Features**: Order creation, status tracking, Kafka event publishing
- **Database**: MongoDB collection `orders`
- **Messaging**: Publishes order events to Kafka

### Inventory Service
- **Purpose**: Stock management, inventory tracking
- **Features**: Stock updates, reservations, availability checks
- **Database**: MongoDB collections `inventory`, `stock_movements`, `reservations`
- **Communication**: gRPC server for inter-service communication

### Notification Service
- **Purpose**: Email notifications, real-time alerts
- **Features**: Order confirmations, stock alerts, user notifications
- **Messaging**: Kafka consumer for order events

### API Gateway
- **Purpose**: Single entry point, request routing, load balancing
- **Features**: Route configuration, CORS handling, health checks
- **Routes**: Proxies requests to appropriate microservices

## API Endpoints

### User Service (via Gateway: http://localhost:8080)
```
POST /api/users/register    # User registration
POST /api/users/login       # User authentication
GET  /api/users/profile     # Get user profile
GET  /api/users/{id}        # Get user by ID (admin)
PUT  /api/users/{id}        # Update user
DELETE /api/users/{id}      # Delete user (admin)
```

### Product Service
```
GET    /api/products              # Get all products (paginated)
GET    /api/products/{id}         # Get product by ID
GET    /api/products/sku/{sku}    # Get product by SKU
GET    /api/products/search?q=    # Search products
GET    /api/products/featured     # Get featured products
POST   /api/products              # Create product
PUT    /api/products/{id}         # Update product
DELETE /api/products/{id}         # Delete product

GET    /api/categories            # Get all categories
GET    /api/categories/{id}       # Get category by ID
POST   /api/categories            # Create category
PUT    /api/categories/{id}       # Update category
DELETE /api/categories/{id}       # Delete category
```

### Order Service
```
GET    /api/orders              # Get all orders
GET    /api/orders/{id}         # Get order by ID
GET    /api/orders/user/{id}    # Get orders by user
POST   /api/orders              # Create order
PUT    /api/orders/{id}         # Update order status
DELETE /api/orders/{id}         # Cancel order
```

### Inventory Service
```
GET    /api/inventory/{productId}    # Get inventory for product
POST   /api/inventory               # Create inventory entry
PUT    /api/inventory/{productId}   # Update inventory
POST   /api/inventory/reserve       # Reserve inventory
POST   /api/inventory/release       # Release reservation
```

## Sample API Usage

### 1. Register a User
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "firstName": "Test",
    "lastName": "User"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### 3. Get Products
```bash
curl http://localhost:8080/api/products
```

### 4. Create a Product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Product",
    "description": "A test product",
    "price": 99.99,
    "categoryName": "Electronics",
    "brand": "TestBrand"
  }'
```

## Monitoring

### Grafana Dashboard
- URL: http://localhost:3000
- Username: admin
- Password: admin

**Available Metrics:**
- Service health and uptime
- Request rates and response times
- Database connection pools
- JVM metrics (for Java services)
- Custom business metrics

### Prometheus
- URL: http://localhost:9090
- Direct access to metrics and alerting

## Development

### Adding New Services
1. Create service directory under `services/`
2. Add Dockerfile
3. Update `docker-compose.yml`
4. Add routes to API Gateway configuration

### Local Development
```bash
# Start infrastructure only
docker-compose up mongodb redis kafka zookeeper prometheus grafana

# Run services locally for development
cd services/user-service && mvn spring-boot:run
cd services/product-service && mvn spring-boot:run
# etc.
```

### Environment Variables
Services can be configured via environment variables:
- `MONGODB_URI`: MongoDB connection string
- `REDIS_HOST`: Redis host
- `KAFKA_BOOTSTRAP_SERVERS`: Kafka brokers
- `JWT_SECRET`: JWT signing secret

## Troubleshooting

### Common Issues

1. **Services not starting**: Check if ports are available
2. **Database connection errors**: Ensure MongoDB is running
3. **Kafka errors**: Wait for Kafka to fully start (can take 1-2 minutes)
4. **Out of memory**: Increase Docker memory allocation

### Logs
```bash
# View logs for specific service
docker-compose logs user-service

# Follow logs
docker-compose logs -f api-gateway

# View all logs
docker-compose logs
```

### Health Checks
All services expose health endpoints:
- User Service: http://localhost:8081/api/users/health
- Product Service: http://localhost:8082/api/products/health
- Order Service: http://localhost:8083/api/orders/health
- Inventory Service: http://localhost:8084/health
- API Gateway: http://localhost:8080/actuator/health

## Production Deployment

### Security Considerations
1. Change default passwords
2. Use environment-specific JWT secrets
3. Enable HTTPS
4. Implement proper authentication middleware
5. Set up network segmentation

### Scaling
- Use Kubernetes for container orchestration
- Implement horizontal pod autoscaling
- Use managed databases (MongoDB Atlas, Redis Cloud)
- Set up load balancers

### Backup
- Regular MongoDB backups
- Redis persistence configuration
- Application logs archival

## Technology Stack Summary

| Component | Technology | Purpose |
|-----------|------------|---------|
| Frontend | Angular 17 + TypeScript | User interface |
| API Gateway | Spring Cloud Gateway | Request routing |
| User Service | Spring Boot + MongoDB + Redis | Authentication |
| Product Service | Spring Boot + MongoDB | Catalog management |
| Order Service | Spring Boot + MongoDB + Kafka | Order processing |
| Inventory Service | Python FastAPI + gRPC | Stock management |
| Notification Service | Python + Kafka | Notifications |
| Database | MongoDB | Data persistence |
| Cache | Redis | Session & data caching |
| Message Queue | Apache Kafka | Async communication |
| Monitoring | Grafana + Prometheus | Observability |
| Containerization | Docker + Docker Compose | Deployment |

This platform demonstrates modern microservices architecture with proper separation of concerns, scalability, and observability.