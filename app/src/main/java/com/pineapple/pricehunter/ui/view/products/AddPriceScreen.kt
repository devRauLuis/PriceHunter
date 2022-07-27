package com.pineapple.pricehunter.ui.view.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pineapple.pricehunter.ui.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPriceScreen(
    id: String? = "",
    popUp: () -> Unit,
    viewModel: ProductsViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    LaunchedEffect(id) {
        println("id arg: $id")
        if (!id.isNullOrEmpty()) {
            viewModel.findProduct(id)
        }
    }

    Column(modifier = Modifier.padding(15.dp, 20.dp)) {
        Text("Agregar precio", style = MaterialTheme.typography.displaySmall)
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = uiState.price.toString(),
                onValueChange = viewModel::setPrice,
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenuBox(
                expanded = uiState.shopDropdownExpanded,
                onExpandedChange = viewModel::toggleShopDropdown,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    readOnly = true,
                    value = uiState.selectedShop?.name ?: "Seleccione una tienda",
                    onValueChange = {},
                    label = { Text("Label") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.shopDropdownExpanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = uiState.shopDropdownExpanded,
                    onDismissRequest = viewModel::toggleShopDropdown,
                ) {
                    uiState.shops.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.name) },
                            onClick = {
                                viewModel.onSelectedShopChange(selectionOption)
                                viewModel.toggleShopDropdown()
                            }
                        )
                    }
                }

            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ElevatedButton(onClick = {
                    viewModel.savePrice(popUp)
                }) {
                    Text("Guardar")
                }
            }
        }
    }

}