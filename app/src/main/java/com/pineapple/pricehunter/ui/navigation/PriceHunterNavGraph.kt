package com.pineapple.pricehunter.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pineapple.pricehunter.ui.PriceHunterAppState
import com.pineapple.pricehunter.ui.view.HomeScreen
import com.pineapple.pricehunter.ui.view.LoginScreen


fun NavGraphBuilder.priceHunterGraph(appState: PriceHunterAppState) {
    composable(Routes.Home.name) {
        HomeScreen(navigate = { route -> appState.navigate(route) })
    }
    composable(Routes.Login.name) {
        LoginScreen(navigate = { route -> appState.navigate(route) })
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