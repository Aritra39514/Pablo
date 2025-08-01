import motor.motor_asyncio
import os
from pymongo import ASCENDING, DESCENDING

class Database:
    client: motor.motor_asyncio.AsyncIOMotorClient = None
    database = None

database = Database()

async def connect_to_mongo():
    """Create database connection"""
    mongodb_uri = os.getenv("MONGODB_URI", "mongodb://admin:password@localhost:27017/inventorydb?authSource=admin")
    database.client = motor.motor_asyncio.AsyncIOMotorClient(mongodb_uri)
    database.database = database.client.get_database()
    
    # Create indexes
    await create_indexes()

async def close_mongo_connection():
    """Close database connection"""
    database.client.close()

async def get_database():
    return database.database

async def create_indexes():
    """Create database indexes for better performance"""
    db = database.database
    
    # Inventory collection indexes
    await db.inventory.create_index([("product_id", ASCENDING)], unique=True)
    await db.inventory.create_index([("sku", ASCENDING)], unique=True)
    await db.inventory.create_index([("status", ASCENDING)])
    await db.inventory.create_index([("quantity", ASCENDING)])
    
    # Stock movements collection indexes
    await db.stock_movements.create_index([("product_id", ASCENDING)])
    await db.stock_movements.create_index([("created_at", DESCENDING)])
    await db.stock_movements.create_index([("reference_id", ASCENDING)])
    
    # Reservations collection indexes
    await db.reservations.create_index([("reservation_id", ASCENDING)], unique=True)
    await db.reservations.create_index([("order_id", ASCENDING)])
    await db.reservations.create_index([("status", ASCENDING)])
    await db.reservations.create_index([("expires_at", ASCENDING)])