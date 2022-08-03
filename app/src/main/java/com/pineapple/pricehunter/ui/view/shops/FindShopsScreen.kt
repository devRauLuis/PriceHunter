package com.pineapple.pricehunter.ui.view.shops

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
fun FindShopsScreen(
    navigate: (String) -> Unit,
    viewModel: ShopsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp)
    ) {
        Text(
            "Busca una tienda",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )
        OutlinedTextField(
            value = uiState.searchField,
            onValueChange = viewModel::setSearchField,
            label = { Text("Buscar") },
            placeholder = { Text("La Sirena") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search"
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.findAllShops(uiState.searchField)
            }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
        if (uiState.shops.isNotEmpty())
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                items(uiState.shops) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { navigate("${Routes.Shop.name}/${it.id}") }
                    ) {

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
