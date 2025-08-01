FROM python:3.11-slim

WORKDIR /app

# Install system dependencies
RUN apt-get update && apt-get install -y \
    gcc \
    && rm -rf /var/lib/apt/lists/*

# Copy requirements and install Python dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy application code
COPY . .

# Generate gRPC code from proto files
RUN python -m grpc_tools.protoc --proto_path=proto --python_out=app/grpc_server --grpc_python_out=app/grpc_server proto/inventory.proto

# Expose ports
EXPOSE 8084 50051

# Run the application
CMD ["python", "-m", "app.main"]