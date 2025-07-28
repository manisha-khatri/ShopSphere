package com.example.shopsphere.data.mapper

import com.example.shopsphere.data.local.ProductEntity
import com.example.shopsphere.data.remote.dto.ProductDto
import com.example.shopsphere.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        isOnSale = isOnSale,
        description = description,
        category = category
    )
}

fun ProductEntity.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        isOnSale = isOnSale,
        description = description,
        category = category
    )
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        isOnSale = isOnSale,
        description = description,
        category = category
    )
}

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        isOnSale = isOnSale,
        description = description,
        category = category
    )
}
