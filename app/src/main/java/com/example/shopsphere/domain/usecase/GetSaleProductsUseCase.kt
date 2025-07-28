package com.example.shopsphere.domain.usecase

import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.repository.ProductRepository
import javax.inject.Inject

class GetSaleProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getSaleProducts()
    }
}
