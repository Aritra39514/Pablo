from pydantic import BaseModel, Field
from typing import Optional, List
from datetime import datetime
from enum import Enum

class OperationType(str, Enum):
    ADD = "ADD"
    SUBTRACT = "SUBTRACT"
    SET = "SET"

class InventoryStatus(str, Enum):
    AVAILABLE = "AVAILABLE"
    LOW_STOCK = "LOW_STOCK"
    OUT_OF_STOCK = "OUT_OF_STOCK"
    DISCONTINUED = "DISCONTINUED"

class InventoryItem(BaseModel):
    id: Optional[str] = Field(None, alias="_id")
    product_id: str
    sku: str
    quantity: int = 0
    reserved: int = 0
    min_stock_level: int = 10
    max_stock_level: int = 1000
    status: InventoryStatus = InventoryStatus.AVAILABLE
    location: Optional[str] = None
    created_at: datetime = Field(default_factory=datetime.utcnow)
    updated_at: datetime = Field(default_factory=datetime.utcnow)

    class Config:
        populate_by_name = True
        json_encoders = {
            datetime: lambda v: v.isoformat()
        }

    @property
    def available_quantity(self) -> int:
        return max(0, self.quantity - self.reserved)

    def update_status(self):
        if self.quantity <= 0:
            self.status = InventoryStatus.OUT_OF_STOCK
        elif self.quantity <= self.min_stock_level:
            self.status = InventoryStatus.LOW_STOCK
        else:
            self.status = InventoryStatus.AVAILABLE

class StockMovement(BaseModel):
    id: Optional[str] = Field(None, alias="_id")
    product_id: str
    operation: OperationType
    quantity: int
    previous_quantity: int
    new_quantity: int
    reason: str
    reference_id: Optional[str] = None  # Order ID, adjustment ID, etc.
    created_at: datetime = Field(default_factory=datetime.utcnow)

    class Config:
        populate_by_name = True
        json_encoders = {
            datetime: lambda v: v.isoformat()
        }

class Reservation(BaseModel):
    id: Optional[str] = Field(None, alias="_id")
    reservation_id: str
    order_id: str
    items: List[dict]  # [{"product_id": str, "quantity": int}]
    status: str = "ACTIVE"  # ACTIVE, RELEASED, EXPIRED
    expires_at: datetime
    created_at: datetime = Field(default_factory=datetime.utcnow)

    class Config:
        populate_by_name = True
        json_encoders = {
            datetime: lambda v: v.isoformat()
        }

# Request/Response models for API
class InventoryUpdateRequest(BaseModel):
    product_id: str
    quantity: int
    operation: OperationType = OperationType.SET
    reason: str = "Manual adjustment"

class InventoryCreateRequest(BaseModel):
    product_id: str
    sku: str
    quantity: int = 0
    min_stock_level: int = 10
    max_stock_level: int = 1000
    location: Optional[str] = None

class ReservationRequest(BaseModel):
    order_id: str
    items: List[dict]  # [{"product_id": str, "quantity": int}]

class AvailabilityRequest(BaseModel):
    items: List[dict]  # [{"product_id": str, "quantity": int}]