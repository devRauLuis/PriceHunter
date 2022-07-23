package com.pineapple.pricehunter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pineapple.pricehunter.ui.view.HomeScreen
import com.pineapple.pricehunter.ui.view.LoginScreen

@Composable
fun PriceHunterNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = Routes.Home.name,
        modifier = modifier
    ) {
        composable(Routes.Home.name) {
            HomeScreen()
        }
        composable(Routes.Login.name) {
            LoginScreen()
        }
    }
}