package com.pineapple.pricehunter.ui.view.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.himanshoe.pluck.ui.Pluck
import com.pineapple.pricehunter.ui.navigation.Routes
import com.pineapple.pricehunter.ui.viewmodel.ProductsViewModel
import kotlin.reflect.KFunction0

@Composable
fun AddProductScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    id: String? = "",
    popUp: KFunction0<Unit>
) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Agregar producto",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )
        TextField(
            value = uiState.name.toString().uppercase(),
            onValueChange = viewModel::setName,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = uiState.photoUrl.toString(),
            onValueChange = viewModel::setPhotoUrl,
            label = { Text("URL de imagen") },
            modifier = Modifier.fillMaxWidth()
        )
        AsyncImage(
            model = uiState.photoUrl,
            contentDescription = uiState.name,
            modifier = Modifier
                .fillMaxWidth(.5f)
                .height(150.dp)
                .padding(top = 10.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                onClick = {
                    viewModel.saveProduct { popUp() }
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "Guardar")
            }
        }
    }
}