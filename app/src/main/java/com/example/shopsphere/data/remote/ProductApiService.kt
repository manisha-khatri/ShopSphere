package com.example.shopsphere.data.remote

import com.example.shopsphere.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {

    @GET("products")
    suspend fun getAllProducts(): List<ProductDto>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): List<ProductDto>

    @GET("products/on-sale")
    suspend fun getSaleProducts(): List<ProductDto>
}
