package com.pineapple.pricehunter.ui.view.products

import androidx.compose.foundation.Indication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pineapple.pricehunter.ui.navigation.Routes
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
        Text("Productos", style = MaterialTheme.typography.displaySmall)
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
                //                    navigate("Products/${searchField}")
            }), modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
        if (uiState.products.size > 0)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            ) {
                items(uiState.products) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clickable(
                                onClick = { navigate("${Routes.Product.name}/${it.id}") }
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            model = it?.photoUrl,
                            contentDescription = it?.name,
                            modifier = Modifier.size(60.dp),
                        )
                        Text(
                            text = it?.name ?: "",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

//                        FilledIconButton(
//                            onClick = { },
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.RemoveRedEye,
//                                contentDescription = "Go", modifier = Modifier.size(30.dp)
//                            )
//                        }
                    }
                }
            }
        else
            Text("...", style = MaterialTheme.typography.headlineSmall)
    }
}