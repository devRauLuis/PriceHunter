package com.pineapple.pricehunter.ui.view.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pineapple.pricehunter.ui.viewmodel.ProductsViewModel

@Composable
fun ProductScreen(id: String?, viewModel: ProductsViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState

    LaunchedEffect(id) {
        println("id arg: $id")
        if (!id.isNullOrEmpty()) {
            viewModel.findProduct(id)
        }
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)) {
        AsyncImage(
            model = uiState.photoUrl,
            contentDescription = uiState.name,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            uiState.name.toString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 15.dp)
        )
    }
}