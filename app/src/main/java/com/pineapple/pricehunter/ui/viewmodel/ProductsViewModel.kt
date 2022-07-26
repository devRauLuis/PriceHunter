package com.pineapple.pricehunter.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pineapple.pricehunter.model.Product
import com.pineapple.pricehunter.model.service.DbService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.pineapple.pricehunter.ui.viewmodel.PriceHunterViewModel
import java.io.IOException

data class UiState(val products: List<Product> = listOf(), val searchField: String = "")

@HiltViewModel
class ProductsViewModel @Inject constructor(val dbService: DbService) : PriceHunterViewModel() {
    var uiState by mutableStateOf(UiState())
        private set


    init {
        viewModelScope.launch {
            try {
                val products = dbService.getAllProducts()
                uiState = uiState.copy(products = products)
            } catch (error: IOException) {
                onError(error)
            }
            Log.d("PRODUCTS VIEW MODEL", uiState.products.toString())
        }
    }

    fun setSearchField(newValue: String) {
        uiState = uiState.copy(searchField = newValue)
    }

}