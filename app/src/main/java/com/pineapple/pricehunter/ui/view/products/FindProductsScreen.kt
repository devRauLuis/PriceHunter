package com.pineapple.pricehunter.ui.view.products

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pineapple.pricehunter.common.utils.formatToCurrency
import com.pineapple.pricehunter.ui.navigation.Routes
import com.pineapple.pricehunter.ui.theme.PriceHunterTheme
import com.pineapple.pricehunter.ui.viewmodel.AuthViewModel
import com.pineapple.pricehunter.ui.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindProductsScreen(
    navigate: (String) -> Unit,
    viewModel: ProductsViewModel = hiltViewModel(), authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val authLoadingState by authViewModel.loadingState.collectAsState()
    val productsLoadingState by viewModel.loadingState.collectAsState()

    val loadingStateList = listOf(authLoadingState, productsLoadingState)

    Log.d("PRICE HUNTER APP ", loadingStateList.toString())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp)
    ) {
        Text(
            "Busca un producto",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )
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
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
//                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
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
                        AsyncImage(
                            model = it.photoUrl,
                            contentDescription = it.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(15.dp, 10.dp)
                        ) {
                            Text(
                                text = it.name.uppercase(),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    lineHeight = 18.sp,
                                ),
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
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 5.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.LocationOn,
                                            contentDescription = "Location icon",
                                            modifier = Modifier.size(15.dp)
                                        )

                                        Text(
                                            text = minPrice!!.shopName,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                lineHeight = 16.sp,
                                            ),
                                            modifier = Modifier.padding(start = 3.dp),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            } else ElevatedButton(
                                onClick = { navigate("${Routes.AddPrice.name}/${it.id}") },
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text(text = "Agregar precio")
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