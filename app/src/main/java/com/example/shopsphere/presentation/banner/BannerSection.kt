package com.example.shopsphere.presentation.banner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.shopsphere.domain.model.Product
import com.example.shopsphere.presentation.product_list.ProductItem

@Composable
fun BannerSection(
    saleProducts: List<Product>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        items(saleProducts) { product ->
            ProductItem(product = product)
        }
    }
}
