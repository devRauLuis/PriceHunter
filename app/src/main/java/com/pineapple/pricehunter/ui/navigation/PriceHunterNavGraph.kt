package com.pineapple.pricehunter.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pineapple.pricehunter.ui.PriceHunterAppState
import com.pineapple.pricehunter.ui.view.AboutScreen
import com.pineapple.pricehunter.ui.view.HomeScreen
import com.pineapple.pricehunter.ui.view.GoogleSignInButton
import com.pineapple.pricehunter.ui.view.products.FindProductsScreen


fun NavGraphBuilder.priceHunterGraph(appState: PriceHunterAppState) {
    composable(Routes.Home.name) {
        HomeScreen(
//            navigate = { route -> appState.navigate(route) },
            restartApp = { appState.clearAndNavigate(Routes.Home.name) })
    }
    composable(Routes.Products.name) {
        FindProductsScreen()
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