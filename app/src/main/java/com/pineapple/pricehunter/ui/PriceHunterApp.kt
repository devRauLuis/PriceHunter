package com.pineapple.pricehunter.ui

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pineapple.pricehunter.common.snackbar.SnackbarManager
import com.pineapple.pricehunter.ui.navigation.Routes
import com.pineapple.pricehunter.ui.navigation.priceHunterGraph
import com.pineapple.pricehunter.ui.theme.PriceHunterTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceHunterApp() {
    PriceHunterTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val appState = rememberAppState()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        appState.snackbarHostState
                    )
                },
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                "Price Hunter", fontSize = 24.sp, fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Localized description"
                                )
                            }
                        },
//                        actions = {
//                            IconButton(onClick = { /* doSomething() */ }) {
//                                Icon(
//                                    imageVector = Icons.Filled.Favorite,
//                                    contentDescription = "Localized description"
//                                )
//                            }
//                        }
                    )
                },

                ) { innerPadding ->
                NavHost(
                    navController = appState.navController,
                    startDestination = Routes.Home.name,
                    modifier = Modifier.padding(innerPadding)
                ) { priceHunterGraph(appState) }
            }

        }
    }
}

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(snackbarHostState, navController, snackbarManager, resources, coroutineScope) {
    PriceHunterAppState(
        snackbarHostState,
        navController,
        snackbarManager,
        resources,
        coroutineScope
    )
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
