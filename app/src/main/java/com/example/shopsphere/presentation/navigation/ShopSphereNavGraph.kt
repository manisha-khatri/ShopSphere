package com.example.shopsphere.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopsphere.presentation.search.SearchScreen

object Routes {
    const val SEARCH = "search"
    const val PRODUCT_LIST = "product_list" // future
}

@Composable
fun ShopSphereNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {
        composable(Routes.SEARCH) {
            SearchScreen(navController)
        }

        // Future route for actual product list (currently placeholder)
        composable(Routes.PRODUCT_LIST) {
            // ProductListScreen(navController)
        }
    }
}
