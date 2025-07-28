package com.example.shopsphere.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val isOnSale: Boolean = false,
    val description: String? = null,
    val category: String? = null
)
