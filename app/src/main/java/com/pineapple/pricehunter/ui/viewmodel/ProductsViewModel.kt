package com.pineapple.pricehunter.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pineapple.pricehunter.common.utils.LoadingState
import com.pineapple.pricehunter.model.Price
import com.pineapple.pricehunter.model.Product
import com.pineapple.pricehunter.model.Shop
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
    val shopId: String = "",
    val shopName: String = "",
    val price: Float = 0F,
    val shopDropdownExpanded: Boolean = false,
    val selectedShop: Shop? = null,
    val shops: List<Shop> = listOf()
)

fun ProductsUiState.toProduct() = Product(
    id = id.toString(),
    name = name.toString().uppercase(),
    photoUrl = photoUrl.toString(),
    prices = prices
)

fun ProductsUiState.toPrice() = Price(
    shopId = shopId,
    shopName = shopName,
    price = price
)

@HiltViewModel
class ProductsViewModel @Inject constructor(val dbService: DbService) : PriceHunterViewModel() {

    var uiState by mutableStateOf(ProductsUiState())
        private set

    init {
        findAllProducts()
        findAllShops()
    }

    fun onSelectedShopChange(shop: Shop) {
        uiState = uiState.copy(selectedShop = shop, shopName = shop.name, shopId = shop.id)
    }

    fun toggleShopDropdown(value: Boolean = false) {
        uiState = uiState.copy(shopDropdownExpanded = !uiState.shopDropdownExpanded)
    }

    fun setShopId(newValue: String) {
        uiState = uiState.copy(shopId = newValue)
    }

    fun setShopName(newValue: String) {
        uiState = uiState.copy(shopName = newValue)
    }

    fun setPrice(newValue: String) {
        uiState = uiState.copy(price = newValue.toFloatOrNull() ?: 0F)
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
            Log.d("PRODUCTS VIEW MODEL", uiState.products.toString())
        }
    }

    fun savePrice(popUp: () -> Unit) {
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                var product = uiState.toProduct()
                val price = uiState.toPrice()
                    .copy(createdAt = Timestamp.now(), createdBy = Firebase.auth.currentUser?.uid)

                // If already exists a price for this shop, update it
                if (product.prices.any { it.shopId == price.shopId }) {
                    product =
                        product.copy(prices = product.prices.filter {
                            it.shopId != price.shopId
                        })
                }
                product = product.copy(prices = product.prices + price)

                val updatedProduct =
                    dbService.updateProduct(product)
                findAllProducts()
                loadingState.emit(LoadingState.LOADED)
                popUp()
            } catch (e: IOException) {
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }

        }
    }


}