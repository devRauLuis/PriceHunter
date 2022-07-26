package com.pineapple.pricehunter.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.pineapple.pricehunter.ui.PriceHunterAppState
import com.pineapple.pricehunter.ui.view.AboutScreen
import com.pineapple.pricehunter.ui.view.HomeScreen
import com.pineapple.pricehunter.ui.view.products.FindProductsScreen
import com.pineapple.pricehunter.ui.view.products.ProductScreen

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
        ProductScreen(id = id)
    }

    composable(Routes.About.name) {
        AboutScreen()
    }

}

//
//@Composable
//fun PriceHunterNavHost(
//    navController: NavHostController,
//    appState: PriceHunterAppState,
//    modifier: Modifier = Modifier,
//) {
//
//    NavHost(
//        navController = navController,
//        startDestination = Routes.Home.name,
//        modifier = modifier
//    ) {
//        composable(Routes.Home.name) {
//            HomeScreen(navigate = { route -> appState.navigate(route) })
//        }
//        composable(Routes.Login.name) {
//            LoginScreen()
//        }
//    }
//}