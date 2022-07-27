package com.pineapple.pricehunter.model

import com.google.firebase.Timestamp

data class Price(
    val price: Float = 0F,
    val shopName: String = "",
    val shopId: String = "",
    val createdAt: Timestamp? = null,
    val createdBy: String? = null,
)
