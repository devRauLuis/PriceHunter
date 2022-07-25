package com.pineapple.pricehunter.ui

import android.content.res.Resources
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pineapple.pricehunter.common.snackbar.SnackbarManager
import com.pineapple.pricehunter.ui.navigation.Routes
import com.pineapple.pricehunter.ui.navigation.priceHunterGraph
import com.pineapple.pricehunter.ui.theme.PriceHunterTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceHunterApp() {
    PriceHunterTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val appState = rememberAppState()
            val scope = rememberCoroutineScope()
//            PriceHunterNavDrawer(drawerState = appState.drawerState)
            val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
            val selectedItem = remember { mutableStateOf(items[0]) }
            ModalNavigationDrawer(
                drawerState = appState.drawerState,
                drawerContent = {
                    items.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item, contentDescription = null) },
                            label = { Text(item.name) },
                            selected = item == selectedItem.value,
                            onClick = {
                                scope.launch { appState.drawerState.close() }
                                selectedItem.value = item
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = if (appState.drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
                        Spacer(Modifier.height(20.dp))
                        Button(onClick = { scope.launch { appState.drawerState.open() } }) {
                            Text("Click to open")
                        }
                    }
                }
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Open),
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
