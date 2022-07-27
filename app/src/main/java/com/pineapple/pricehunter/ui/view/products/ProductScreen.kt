package com.pineapple.pricehunter.ui.view.products

import android.app.Dialog
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pineapple.pricehunter.common.utils.formatToCurrency
import com.pineapple.pricehunter.model.Product
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

        AsyncImage(
            model = uiState.photoUrl,
            contentDescription = uiState.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
        )
        Text(
            uiState.name.toString().uppercase(),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 15.dp)
        )
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                onClick = { navigate("${Routes.AddPrice.name}/${uiState.id}") },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "Agregar precio")
            }
        }
    }
}
