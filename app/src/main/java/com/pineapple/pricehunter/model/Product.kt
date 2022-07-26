package com.pineapple.pricehunter.model

import com.pineapple.pricehunter.ui.viewmodel.ProductsUiState

data class Product(
    val id: String = "",
    val name: String = "",
    val photoUrl: String = "",
    val prices: List<Price> = emptyList(),
)

fun Product.toProductsUiState(uiState: ProductsUiState) =
    uiState.copy(id = id, name = name, photoUrl = photoUrl, prices = prices)