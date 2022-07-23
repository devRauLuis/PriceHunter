package com.pineapple.pricehunter.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class Routes(
    val icon: ImageVector,
    val displayText: String = "",
) {
    Home(
        icon = Icons.Filled.Home,
        displayText = "Inicio"
    ),
    Login(
        icon = Icons.Filled.Person,
        displayText = "Registro Criptomoneda",
    );

    companion object {
        fun fromRoute(route: String?): Routes =
            when (route?.substringBefore("/")) {
                Home.name -> Home
                Login.name -> Login
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }

}