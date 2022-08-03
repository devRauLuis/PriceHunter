package com.pineapple.pricehunter.ui.view.shops

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pineapple.pricehunter.ui.viewmodel.ShopsViewModel

@Composable
fun AddShopScreen(
    viewModel: ShopsViewModel = hiltViewModel(),
    id: String? = "",
    popUp: () -> Unit
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
            "Agregar tienda",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )
        TextField(
            value = uiState.name.toString().uppercase(),
            onValueChange = viewModel::setName,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = uiState.city.toString(),
            onValueChange = viewModel::setCity,
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = uiState.address.toString(),
            onValueChange = viewModel::setAddress,
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                onClick = {
                    viewModel.saveShop { popUp() }
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