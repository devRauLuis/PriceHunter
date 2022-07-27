package com.pineapple.pricehunter.model

import com.google.firebase.Timestamp
import com.pineapple.pricehunter.ui.viewmodel.ProductsUiState

data class Product(
    val id: String = "",
    val name: String = "",
    val photoUrl: String = "",
    val prices: List<Price> = emptyList(),
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null,
    val createdBy: String? = null,
    val updatedBy: String? = null
)

fun Product.toProductsUiState(uiState: ProductsUiState) =
    uiState.copy(id = id, name = name, photoUrl = photoUrl, prices = prices)