package com.example.shopsphere.domain.usecase

import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.repository.SearchRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<Product> {
        return repository.getProducts(query)
    }
}