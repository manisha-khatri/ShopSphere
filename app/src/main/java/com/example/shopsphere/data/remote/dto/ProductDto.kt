package com.example.shopsphere.data.remote.dto

data class ProductDto(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val isOnSale: Boolean,
    val description: String?,
    val category: String?
)