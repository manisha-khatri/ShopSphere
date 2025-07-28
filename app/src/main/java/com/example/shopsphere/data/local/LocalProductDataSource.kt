package com.example.shopsphere.data.local

interface LocalProductDataSource {
    suspend fun saveProducts(products: List<ProductEntity>)
    suspend fun getAllProducts(): List<ProductEntity>
    suspend fun clearProducts()
    suspend fun searchProducts(query: String): List<ProductEntity>
    suspend fun getSaleProducts(): List<ProductEntity>
}
