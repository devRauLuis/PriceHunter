package com.pineapple.pricehunter.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.common.io.Files.append
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pineapple.pricehunter.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceHunterNavDrawer(
    drawerState: DrawerState,
    navigate: (String) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    content: @Composable() () -> Unit
) {
    val scope = rememberCoroutineScope()
// icons to mimic drawer destinations
    val navItems = Routes.values().toList().filter { !it.hidden }
    val selectedItem = remember { mutableStateOf(navItems[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (authViewModel.isUserAuthenticated)
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 20.sp)) { append("Welcome, ") }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) { append(Firebase.auth.currentUser?.displayName ?: "") }
                }, modifier = Modifier.padding(top = 20.dp, start = 20.dp))
            else Text("")

            Column(modifier = Modifier.padding(top = 15.dp)) {
                navItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { item.icon?.let { Icon(it, contentDescription = null) } },
                        label = { Text(item.name) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                            navigate(item.name)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
    ) {
        content()
    }
}