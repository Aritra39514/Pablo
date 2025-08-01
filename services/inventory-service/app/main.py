from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import uvicorn
import asyncio
import os
from contextlib import asynccontextmanager

from app.services.inventory_service import InventoryService
from app.routes import inventory_routes
from app.grpc_server.server import start_grpc_server


@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup
    print("Starting Inventory Service...")
    
    # Start gRPC server in background
    grpc_task = asyncio.create_task(start_grpc_server())
    
    yield
    
    # Shutdown
    print("Shutting down Inventory Service...")
    grpc_task.cancel()
    try:
        await grpc_task
    except asyncio.CancelledError:
        pass

app = FastAPI(
    title="Inventory Service",
    description="Inventory Management Service for Ecommerce Platform",
    version="1.0.0",
    lifespan=lifespan
)

# CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Include routes
app.include_router(inventory_routes.router, prefix="/api/inventory", tags=["inventory"])

@app.get("/")
async def root():
    return {"message": "Inventory Service is running"}

@app.get("/health")
async def health_check():
    return {
        "status": "UP",
        "service": "inventory-service",
        "version": "1.0.0"
    }

if __name__ == "__main__":
    uvicorn.run(
        "main:app",
        host="0.0.0.0",
        port=int(os.getenv("PORT", 8084)),
        reload=True
    )