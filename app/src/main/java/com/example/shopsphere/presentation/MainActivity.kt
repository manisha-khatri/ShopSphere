package com.example.shopsphere.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.shopsphere.presentation.navigation.ShopSphereNavGraph
import com.example.shopsphere.presentation.theme.ShopSphereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopSphereTheme {
                ShopSphereNavGraph()
            }
        }
    }
}
