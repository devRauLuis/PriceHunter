package com.pineapple.pricehunter.ui.view.products

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pineapple.pricehunter.common.utils.formatToCurrency
import com.pineapple.pricehunter.ui.navigation.Routes
import com.pineapple.pricehunter.ui.theme.PriceHunterTheme
import com.pineapple.pricehunter.ui.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindProductsScreen(
    navigate: (String) -> Unit,
    viewModel: ProductsViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp)
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
            keyboardActions = KeyboardActions(onSearch = {
                Log.d("FindProductsScreen", "Search called")
                viewModel.findAllProducts(uiState.searchField)
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
        if (uiState.products.isNotEmpty())
            LazyColumn(
//                columns = GridCells.Adaptive(minSize = 128.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                items(uiState.products) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { navigate("${Routes.Product.name}/${it.id}") }
                    ) {
                        Row() {
                            AsyncImage(
                                model = it.photoUrl,
                                contentDescription = it.name,
                                modifier = Modifier.width(120.dp)
                            )
                            Column(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
                            ) {
                                Text(
                                    text = it.name, style = MaterialTheme.typography.titleLarge
                                )

                                val minPrice by remember { mutableStateOf(it.prices.minByOrNull { it.price }) }
                                if (minPrice != null) {
                                    Column(modifier = Modifier.padding(top = 5.dp)) {
                                        Text(
                                            text = "Menor precio:",
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                        Text(
                                            text = formatToCurrency(minPrice!!.price),
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        )
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Filled.LocationOn,
                                                contentDescription = "Location icon",
                                                modifier = Modifier.size(15.dp)
                                            )
                                            Text(
                                                text = minPrice!!.shopName,
                                                style = MaterialTheme.typography.titleSmall
                                            )
                                        }
                                    }
                                } else ElevatedButton(onClick = {}) {
                                    Text(text = "Agregar precio")
                                }
                            }
                        }
                    }
                }
            }
        else if (uiState.searchField.isNotBlank()) {
            Box(modifier = Modifier.padding(top = 10.dp)) {
                Text(
                    "No se han encontrado resultados",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
                )
            }
        } else
            Text(
                "...",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
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