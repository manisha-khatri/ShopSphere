package com.example.shopsphere.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopsphere.presentation.product_list.ProductListScreen
import com.example.shopsphere.presentation.search.SearchScreen

@Composable
fun ShopSphereNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "home") {
        composable("home") {
            ProductListScreen(navController)
        }
        composable("search") {
            SearchScreen(navController)
        }
    }
}
