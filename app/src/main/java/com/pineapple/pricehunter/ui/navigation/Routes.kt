package com.pineapple.pricehunter.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class Routes(
    val icon: ImageVector,
    val displayText: String = "",
) {
    Home(
        icon = Icons.Filled.Home,
        displayText = "Inicio"
    ),
    Products(icon = Icons.Filled.CurrencyExchange, displayText = "Productos"),
    About(
        icon = Icons.Filled.Info,
        displayText = "Sobre nosotros"
    );

    companion object {
        fun fromRoute(route: String?): Routes =
            when (route?.substringBefore("/")) {
                Home.name -> Home
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }

}