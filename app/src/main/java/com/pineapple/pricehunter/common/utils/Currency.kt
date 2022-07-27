package com.pineapple.pricehunter.common.utils

import java.text.NumberFormat
import java.util.*

fun formatToCurrency(n: Float): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance("USD")
    return format.format(n)
}