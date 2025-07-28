package com.example.shopsphere.domain.repository

import com.example.shopsphere.domain.model.Product

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun searchProducts(query: String): List<Product>
    suspend fun getSaleProducts(): List<Product>
}
