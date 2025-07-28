package com.example.shopsphere.data.remote

import com.example.shopsphere.data.remote.dto.ProductDto

interface RemoteProductDataSource {
    suspend fun fetchAllProducts(): List<ProductDto>
    suspend fun searchProducts(query: String): List<ProductDto>
    suspend fun fetchSaleProducts(): List<ProductDto>
}
