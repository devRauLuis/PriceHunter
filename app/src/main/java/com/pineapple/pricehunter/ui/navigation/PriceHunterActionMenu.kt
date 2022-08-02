package com.pineapple.pricehunter.ui.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.pineapple.pricehunter.ui.components.ActionMenu
import com.pineapple.pricehunter.ui.viewmodel.ProductsViewModel
import kotlin.reflect.KFunction1

@Composable
fun PriceHunterActionMenu(
    route: String,
    navigate: (String) -> Unit,
    productsViewModel: ProductsViewModel = hiltViewModel()
) {
    val id = productsViewModel.id

    LaunchedEffect(id) {
        Log.d("PriceHunterActionMenu", "id: $id")
    }

    when {
        route.contains(Routes.Products.name) -> {
            ActionMenu {
                DropdownMenuItem(
                    text = { Text("Agregar producto") },
                    onClick = {
                        navigate(Routes.AddProduct.name)
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = null
                        )
                    })
            }
        }
        route.contains(Routes.Product.name) -> {
            ActionMenu {
                DropdownMenuItem(
                    text = { Text("Agregar precio") },
                    onClick = {
                        navigate("${Routes.AddPrice.name}/${id}")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = null
                        )
                    })
            }
        }

        else -> {}
    }

}