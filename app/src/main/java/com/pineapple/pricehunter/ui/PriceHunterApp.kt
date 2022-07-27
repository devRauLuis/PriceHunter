package com.pineapple.pricehunter.ui

import android.content.res.Resources
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pineapple.pricehunter.common.snackbar.SnackbarManager
import com.pineapple.pricehunter.common.utils.LoadingState
import com.pineapple.pricehunter.ui.navigation.PriceHunterNavDrawer
import com.pineapple.pricehunter.ui.navigation.Routes
import com.pineapple.pricehunter.ui.navigation.priceHunterGraph
import com.pineapple.pricehunter.ui.theme.PriceHunterTheme
import com.pineapple.pricehunter.ui.viewmodel.AuthViewModel
import com.pineapple.pricehunter.ui.viewmodel.PriceHunterViewModel
import com.pineapple.pricehunter.ui.viewmodel.ProductsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceHunterApp(
    priceHunterViewModel: PriceHunterViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    productsViewModel: ProductsViewModel = hiltViewModel()
) {
    val appState = rememberAppState()
    val scope = rememberCoroutineScope()
    val authLoadingState by authViewModel.loadingState.collectAsState()
    val productsLoadingState by productsViewModel.loadingState.collectAsState()
    val loadingStateList = listOf(authLoadingState, productsLoadingState)
    PriceHunterTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            PriceHunterNavDrawer(
                drawerState = appState.drawerState,
                navigate = { appState.navigate(it) }
            ) {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            appState.snackbarHostState
                        )
                    },
                    topBar = {
                        Column {
                            if (loadingStateList.any { it.status == LoadingState.Status.RUNNING }) {
                                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                            }
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        "Price Hunter",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            appState.drawerState.open()
                                        }
                                    }) {
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
                        }
                    },
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        NavHost(
                            navController = appState.navController,
                            startDestination = Routes.Home.name,
                        ) {
                            priceHunterGraph(appState)
                        }
                    }
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(snackbarHostState, navController, snackbarManager, resources, coroutineScope) {
    PriceHunterAppState(
        snackbarHostState,
        navController,
        drawerState,
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
