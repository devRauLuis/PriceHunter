package com.pineapple.pricehunter.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.pineapple.pricehunter.ui.PriceHunterAppState
import com.pineapple.pricehunter.ui.view.AboutScreen
import com.pineapple.pricehunter.ui.view.HomeScreen
import com.pineapple.pricehunter.ui.view.products.AddPriceScreen
import com.pineapple.pricehunter.ui.view.products.AddProductScreen
import com.pineapple.pricehunter.ui.view.products.FindProductsScreen
import com.pineapple.pricehunter.ui.view.products.ProductScreen
import com.pineapple.pricehunter.ui.view.shops.AddShopScreen
import com.pineapple.pricehunter.ui.view.shops.FindShopsScreen
import com.pineapple.pricehunter.ui.view.shops.ShopScreen

fun NavGraphBuilder.priceHunterGraph(appState: PriceHunterAppState) {

    composable(Routes.Home.name) {
        HomeScreen(
            navigate = { route -> appState.navigate(route) },
            restartApp = { appState.clearAndNavigate(Routes.Home.name) })
    }

    composable(Routes.Products.name) {
        FindProductsScreen(
            navigate = { route -> appState.navigate(route) },
        )
    }

    composable(Routes.AddPrice.name) {
        AddPriceScreen(
            popUp = { appState.popUp() }
        )
    }

    val addPriceScreen = Routes.AddPrice.name
    composable(
        route = "$addPriceScreen/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "app://$addPriceScreen/{id}"
            }
        ),
    ) {
        val id = it.arguments?.getString("id")
        println("id arg: $id")
        AddPriceScreen(id = id, popUp = { appState.popUp() })
    }

    val productScreen = Routes.Product.name
    composable(
        route = "$productScreen/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "app://$productScreen/{id}"
            }
        ),

        ) {
        val id = it.arguments?.getString("id")
        println("id arg: $id")
        ProductScreen(id = id, navigate = { route -> appState.navigate(route) })
    }

    val addProductScreen = Routes.AddProduct.name
    composable(
        route = "$addProductScreen/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "app://$addProductScreen/{id}"
            }
        ),
    ) {
        val id = it.arguments?.getString("id")
        println("id arg: $id")
        AddProductScreen(id = id, popUp = appState::popUp)
    }

    composable(Routes.AddProduct.name) {
        AddProductScreen(
            popUp = appState::popUp
        )
    }

    composable(Routes.About.name) {
        AboutScreen()
    }

    composable(Routes.Shops.name) {
        FindShopsScreen(
            navigate = { route -> appState.navigate(route) },
        )
    }

    composable(Routes.AddShop.name) {
        AddShopScreen(
            popUp = appState::popUp
        )
    }

    val addShopScreen = Routes.AddShop.name
    composable(
        route = "$addShopScreen/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "app://$addShopScreen/{id}"
            }
        ),
    ) {
        val id = it.arguments?.getString("id")
        println("id arg: $id")
        AddShopScreen(id = id, popUp = appState::popUp)
    }

    val shopScreen = Routes.Shop.name
    composable(
        route = "$shopScreen/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "app://$shopScreen/{id}"
            }
        ),
        ) {
        val id = it.arguments?.getString("id")
        println("id arg: $id")
        ShopScreen(id = id, navigate = { route -> appState.navigate(route) })
    }


}