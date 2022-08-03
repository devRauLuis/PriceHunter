package com.pineapple.pricehunter.model

import com.pineapple.pricehunter.ui.viewmodel.ShopsUiState
import com.google.firebase.Timestamp

data class Shop(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val city: String = "",
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null,
    val createdBy: String? = null,
    val updatedBy: String? = null
)

fun Shop.toShopUiState(uiState: ShopsUiState) =
    uiState.copy(id = id, name = name, address = address, city = city)