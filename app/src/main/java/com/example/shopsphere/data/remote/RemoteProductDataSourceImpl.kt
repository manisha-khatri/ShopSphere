package com.example.shopsphere.data.remote

import com.example.shopsphere.data.remote.dto.ProductDto
import javax.inject.Inject

class RemoteProductDataSourceImpl @Inject constructor(
    private val apiService: ProductApiService
) : RemoteProductDataSource {

    override suspend fun fetchAllProducts(): List<ProductDto> {
        return apiService.getAllProducts()
    }

    override suspend fun searchProducts(query: String): List<ProductDto> {
        return apiService.searchProducts(query)
    }

    override suspend fun fetchSaleProducts(): List<ProductDto> {
        return apiService.getSaleProducts()
    }
}
