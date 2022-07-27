package com.pineapple.pricehunter.ui.view.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pineapple.pricehunter.ui.navigation.Routes
import com.pineapple.pricehunter.ui.theme.PriceHunterTheme
import com.pineapple.pricehunter.ui.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindProductsScreen(navigate: (String) -> Unit, viewModel: ProductsViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text("Busca un producto", style = MaterialTheme.typography.displaySmall)
        OutlinedTextField(
            value = uiState.searchField,
            onValueChange = viewModel::setSearchField,
            label = { Text("Buscar") },
            placeholder = { Text("Nesquik 56oz") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search"
                )

            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.findAllProducts(uiState.searchField)
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
        if (uiState.products.size > 0)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 320.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(uiState.products) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clickable(
                                onClick = { navigate("${Routes.Product.name}/${it.id}") }
                            ),
                    ) {
                        AsyncImage(
                            model = it.photoUrl,
                            contentDescription = it.name,
                        )
                        Text(
                            text = it.name, style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = it.prices.minOf { it.price }.toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        else
            Text(
                "...",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
    }
}

@Preview(showBackground = true)
@Composable
fun FindProductsScreenPreview() {
    PriceHunterTheme {
        FindProductsScreen(navigate = {})
    }
}