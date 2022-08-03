package com.pineapple.pricehunter.ui.view.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pineapple.pricehunter.common.utils.formatToCurrency
import com.pineapple.pricehunter.ui.navigation.Routes
import com.pineapple.pricehunter.ui.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    id: String?,
    navigate: (String) -> Unit,
    viewModel: ProductsViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState

    LaunchedEffect(id) {
        println("id arg: $id")
        if (!id.isNullOrEmpty()) {
            viewModel.findProduct(id)
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
        ) {
            AsyncImage(
                model = uiState.photoUrl,
                contentDescription = uiState.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
            )
//            Column(
//                verticalArrangement = Arrangement.Bottom,
//                horizontalAlignment = Alignment.End,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(end = 20.dp)
//            ) {
//
//            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                uiState.name.toString().uppercase(),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp, lineHeight = 24.sp
                ), modifier = Modifier.fillMaxWidth(.65f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 25.dp)
            ) {
                FilledTonalIconButton(
                    onClick = { navigate("${Routes.AddProduct.name}/${uiState.id}") },
                    modifier = Modifier.size(40.dp),
                ) {
                    Icon(
                        Icons.Filled.Edit, contentDescription = "Editar",
                    )
                }

                FilledTonalIconButton(
                    onClick = { navigate("${Routes.AddPrice.name}/${uiState.id}") },
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(40.dp)
                ) {
                    Icon(
                        Icons.Filled.AttachMoney,
                        contentDescription = "Agregar precio",
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier.padding(top = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val sortedPrices = uiState.prices.sortedBy { it.price }
            items(sortedPrices) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        if (it == sortedPrices[0])
                            Text(
                                text = "Menor precio",
                                style = MaterialTheme.typography.labelLarge
                            )
                        Text(
                            text = formatToCurrency(it.price),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 5.dp)
                        ) {
                            Icon(
                                Icons.Filled.LocationOn,
                                "Location icon",
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = it.shopName,
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }

                    }


                }
            }
        }
    }
}
