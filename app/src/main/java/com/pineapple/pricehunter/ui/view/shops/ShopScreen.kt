package com.pineapple.pricehunter.ui.view.shops

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pineapple.pricehunter.common.utils.formatToCurrency
import com.pineapple.pricehunter.ui.navigation.Routes
import com.pineapple.pricehunter.ui.viewmodel.ShopsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    id: String?,
    navigate: (String) -> Unit,
    viewModel: ShopsViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState

    LaunchedEffect(id) {
        println("id arg: $id")
        if (!id.isNullOrEmpty()) {
            viewModel.findShop(id)
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
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
                    onClick = { navigate("${Routes.AddShop.name}/${uiState.id}") },
                    modifier = Modifier.size(40.dp),
                ) {
                    Icon(
                        Icons.Filled.Edit, contentDescription = "Editar",
                    )
                }
            }
        }
        if (uiState.products.isNotEmpty())
            LazyColumn(
                modifier = Modifier.padding(top = 15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
                            val price = it.prices[0]
                            Column(modifier = Modifier.padding(top = 5.dp)) {
                                Text(
                                    text = formatToCurrency(price.price),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        else
            Column(
                modifier = Modifier.padding(top = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No hay productos",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                ElevatedButton(
                    onClick = { navigate(Routes.Products.name) },
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text("Ver productos")
                }
            }
    }
}
