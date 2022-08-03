package com.pineapple.pricehunter.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.pineapple.pricehunter.common.snackbar.SnackbarManager
import com.pineapple.pricehunter.common.utils.LoadingState
import com.pineapple.pricehunter.model.Product
import com.pineapple.pricehunter.model.Shop
import com.pineapple.pricehunter.model.service.DbService
import com.pineapple.pricehunter.model.toProductsUiState
import com.pineapple.pricehunter.model.toShopUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

data class ShopsUiState(
    val shops: List<Shop> = listOf(),
    val searchField: String = "",
    val id: String? = "",
    val name: String? = "",
    val address: String? = "",
    val city: String? = "",
    val products: List<Product> = listOf()
)

fun ShopsUiState.toShop() = Shop(
    id = id.toString(),
    name = name.toString().uppercase(),
    address = address.toString(),
    city = city.toString()
)

@HiltViewModel
class ShopsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val dbService: DbService
) : PriceHunterViewModel() {

    var uiState by mutableStateOf(ShopsUiState())
        private set

    var id by mutableStateOf("")

    init {
        Log.d("SAVED STATE HANDLE", savedStateHandle.get<String>("id") ?: "")
        id = savedStateHandle.get<String>("id") ?: ""
        findAllShops()
    }

    fun setName(newValue: String) {
        uiState = uiState.copy(name = newValue)
    }

    fun setCity(newValue: String) {
        uiState = uiState.copy(city = newValue)
    }

    fun setAddress(newValue: String) {
        uiState = uiState.copy(address = newValue)
    }

    fun setSearchField(newValue: String) {
        uiState = uiState.copy(searchField = newValue)
    }

    fun findAllShops(nameQuery: String? = "") {
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                val shops = dbService.getAllShops(nameQuery)
                uiState = uiState.copy(shops = shops)
                loadingState.emit(LoadingState.LOADED)
            } catch (e: IOException) {
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }
            Log.d("SHOPS VIEW MODEL", uiState.shops.toString())
        }
    }

    fun findShop(id: String) {
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                val shop = dbService.getShop(id)
                var products = dbService.getAllProducts()

                // Filter products to obtain only the ones that belong to the shop
                products =
                    products.filter { product -> product.prices.any { price -> price.shopId == id } }
                        .map { product -> product.copy(prices = product.prices.filter { price -> price.shopId == id }) }

                if (shop != null) {
                    uiState = shop.toShopUiState(uiState).copy(products = products)
                }
                loadingState.emit(LoadingState.LOADED)
            } catch (e: IOException) {
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }
            Log.d("SHOPS VIEW MODEL", uiState.toShop().toString())
        }
    }

    fun saveShop(popUp: () -> Unit) {
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                var shop = uiState.toShop()

                if (shop.name.isBlank() || shop.name.length < 3) {
                    SnackbarManager.showMessage("El nombre es requerido con mas de 3 letras")
                    return@launch
                } else if (shop.address.isBlank() || shop.address.length < 3) {
                    SnackbarManager.showMessage("La dirección es requerida con mas de 3 letras")
                    return@launch
                } else if (shop.city.isBlank() || shop.city.length < 3) {
                    SnackbarManager.showMessage("La ciudad es requerida con mas de 3 letras")
                    return@launch
                }
                if (uiState.id.isNullOrBlank()) {
                    shop = dbService.createShop(shop)!!
                } else {
                    shop = dbService.updateShop(shop)!!
                }
                uiState = uiState.copy(shops = uiState.shops + shop)
                loadingState.emit(LoadingState.LOADED)
                popUp()
            } catch (e: IOException) {
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }
        }
    }
}