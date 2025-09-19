package com.example.shopsphere.domain.usecase

import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.domain.repository.SearchRepository
import com.example.shopsphere.util.Result
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): Result<List<Product>> {
        return when (val result = repository.getProducts(query)) {
            is Result.Success -> {
                val filtered = result.data
                    .filter { it.name.isNotBlank() && it.price >= 0 }
                    .sortedBy { it.price }
                    .take(20)
                Result.Success(filtered)
            }
            is Result.Failure -> result
        }
    }
}
