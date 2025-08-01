# Ecommerce Platform - Microservices Architecture

A comprehensive end-to-end ecommerce platform built with modern technologies and microservices architecture.

## Technologies Used

- **Frontend**: Angular 17+ with TypeScript
- **Backend Services**: Java Spring Boot, Python FastAPI
- **Databases**: MongoDB (NoSQL)
- **Caching**: Redis
- **Message Queue**: Apache Kafka
- **Communication**: gRPC, REST APIs
- **Monitoring**: Grafana, Prometheus
- **Containerization**: Docker & Docker Compose

## Services Architecture

### 1. API Gateway (Java Spring Cloud Gateway)
- Routes requests to appropriate microservices
- Authentication & authorization
- Rate limiting and load balancing

### 2. User Service (Java Spring Boot)
- User registration, authentication, and profile management
- JWT token management
- Redis session storage

### 3. Product Catalog Service (Java Spring Boot)
- Product management (CRUD operations)
- Category management
- Search and filtering capabilities

### 4. Order Service (Java Spring Boot)
- Order creation and management
- Payment processing simulation
- Kafka integration for order events

### 5. Inventory Service (Python FastAPI)
- Stock management
- gRPC communication with other services
- Real-time inventory updates

### 6. Notification Service (Python)
- Email/SMS notifications
- Kafka consumer for order events
- Real-time notifications

### 7. Frontend (Angular + TypeScript)
- Modern responsive UI
- Product browsing and search
- Shopping cart and checkout
- User dashboard

## Getting Started

1. Clone the repository
2. Run `docker-compose up` to start all services
3. Access the application at `http://localhost:4200`

## API Documentation

- API Gateway: `http://localhost:8080`
- User Service: `http://localhost:8081`
- Product Service: `http://localhost:8082`
- Order Service: `http://localhost:8083`
- Inventory Service: `http://localhost:8084`

## Monitoring

- Grafana Dashboard: `http://localhost:3000`
- Prometheus: `http://localhost:9090`
