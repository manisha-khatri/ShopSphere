package com.example.shopsphere.data.network

data class ProductsResponse(
    val products: List<ProductDto>
)

data class ProductDto(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String
)