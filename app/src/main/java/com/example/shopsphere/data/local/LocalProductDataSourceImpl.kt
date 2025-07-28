package com.example.shopsphere.data.local

import javax.inject.Inject

class LocalProductDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
) : LocalProductDataSource {

    override suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAll()
    }

    override suspend fun searchProducts(query: String): List<ProductEntity> {
        return productDao.search(query)
    }

    override suspend fun getSaleProducts(): List<ProductEntity> {
        return productDao.getOnSale()
    }

    override suspend fun saveProducts(products: List<ProductEntity>) {
        productDao.insertAll(products)
    }

    override suspend fun clearProducts() {
        productDao.clear()
    }
}
