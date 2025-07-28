package com.example.shopsphere.domain.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val isOnSale: Boolean,
    val description: String?,
    val category: String?
)
