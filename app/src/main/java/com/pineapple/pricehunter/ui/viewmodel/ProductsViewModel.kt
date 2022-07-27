package com.pineapple.pricehunter.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.pineapple.pricehunter.common.utils.LoadingState
import com.pineapple.pricehunter.model.Price
import com.pineapple.pricehunter.model.Product
import com.pineapple.pricehunter.model.service.DbService
import com.pineapple.pricehunter.model.toProductsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

data class ProductsUiState(
    val products: List<Product> = listOf(),
    val searchField: String = "",
    val id: String? = "",
    val name: String? = "",
    val photoUrl: String? = "",
    val prices: List<Price> = listOf(),
)

fun ProductsUiState.toProduct() = Product(
    id = id.toString(),
    name = name.toString(),
    photoUrl = photoUrl.toString(),
    prices = prices
)

@HiltViewModel
class ProductsViewModel @Inject constructor(val dbService: DbService) : PriceHunterViewModel() {

    var uiState by mutableStateOf(ProductsUiState())
        private set

    init {
        findAllProducts()
    }

    fun setSearchField(newValue: String) {
        uiState = uiState.copy(searchField = newValue)
    }

    fun findAllProducts(nameQuery: String? = "") {
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                val products = dbService.getAllProducts(nameQuery)
                uiState = uiState.copy(products = products)
                loadingState.emit(LoadingState.LOADED)
            } catch (e: IOException) {
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }
            Log.d("PRODUCTS VIEW MODEL", uiState.products.toString())
        }
    }

    fun findProduct(id: String) {
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                val product = dbService.getProduct(id)
                if (product != null)
                    uiState = product.toProductsUiState(uiState)
                loadingState.emit(LoadingState.LOADED)
            } catch (e: IOException) {
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }
            Log.d("PRODUCTS VIEW MODEL", uiState.toProduct().toString())
        }
    }

}