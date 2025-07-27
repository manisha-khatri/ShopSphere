package com.example.shopsphere.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.shopsphere.domain.Product


@Composable
fun BannerList(banners: List<Product>) {
    LazyColumn {
        items(banners) { banner ->
            Text(text = banner.name)
        }
    }
}