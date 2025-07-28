package com.example.shopsphere.data.repository

import com.example.shopsphere.data.local.LocalProductDataSource
import com.example.shopsphere.data.mapper.toDomain
import com.example.shopsphere.data.mapper.toEntity
import com.example.shopsphere.data.remote.RemoteProductDataSource
import com.example.shopsphere.data.remote.dto.ProductDto
import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.repository.ProductRepository
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteProductDataSource,
    private val localDataSource: LocalProductDataSource
) : ProductRepository {

    override suspend fun getAllProducts(): List<Product> {
        return try {
            val remoteProducts: List<ProductDto> = remoteDataSource.fetchAllProducts()
            // Explicitly specify the type of 'dto' to resolve ambiguity
            val entities = remoteProducts.map { dto: ProductDto -> dto.toEntity() }


            // Cache in DB
            localDataSource.clearProducts()
            localDataSource.saveProducts(entities)

            // Return domain models
            remoteProducts.map { it.toDomain() }
        } catch (e: Exception) {
            // Fallback to local cache
            localDataSource.getAllProducts().map { it.toDomain() }
        }
    }

    override suspend fun searchProducts(query: String): List<Product> {
        // Let's say search is local-only for better UX
        return localDataSource.searchProducts(query).map { it.toDomain() }
    }

    override suspend fun getSaleProducts(): List<Product> {
        return try {
            remoteDataSource.fetchSaleProducts().map { it.toDomain() }
        } catch (e: Exception) {
            // Optional fallback: local sale products
            localDataSource.getSaleProducts().map { it.toDomain() }
        }
    }
}