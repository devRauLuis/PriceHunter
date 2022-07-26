package com.pineapple.pricehunter.model

data class Product(
    val id: String = "",
    val name: String = "",
    val photoUrl: String = "",
    val prices: List<Price> = emptyList(),
)
