package com.pineapple.pricehunter.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class Routes(
    val icon: ImageVector? = null,
    val displayText: String = "",
    val hidden: Boolean = true
) {
    Home(
        icon = Icons.Filled.Home,
        displayText = "Inicio", hidden = false

    ),
    Products(icon = Icons.Filled.CurrencyExchange, displayText = "Productos", hidden = false),
    Shops(icon = Icons.Filled.ShoppingBag, displayText = "Tiendas", hidden = false),
    About(
        icon = Icons.Filled.Info,
        displayText = "Sobre nosotros", hidden = false
    ),
    Product(displayText = "Producto"),
    AddPrice(displayText = "Agregar precio"),
    AddProduct(displayText = "Agregar producto"),
    Shop(displayText = "Tienda"),
    AddShop(displayText = "Agregar tienda");

    companion object {
        fun fromRoute(route: String?): Routes =
            when (route?.substringBefore("/")) {
                Home.name -> Home
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }

}