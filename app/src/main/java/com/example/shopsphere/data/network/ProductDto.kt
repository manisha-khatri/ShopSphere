package com.example.shopsphere.data.network

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    val products: List<ProductDto>
)

data class ProductDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("imageUrl") val imageUrl: String
)