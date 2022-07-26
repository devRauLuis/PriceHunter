/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.pineapple.pricehunter.model.service

import com.pineapple.pricehunter.model.Product
import com.pineapple.pricehunter.model.Shop

interface DbService {
    suspend fun getAllProducts(query: String? = ""): List<Product>
    suspend fun getProduct(id: String): Product?
    suspend fun updateProduct(product: Product): Product?
    suspend fun updateShop(shop: Shop): Shop?
    suspend fun createProduct(product: Product): Product?
    suspend fun createShop(shop: Shop): Shop?

    suspend fun getAllShops(query: String? = ""): List<Shop>
    suspend fun getShop(id: String): Shop?
    suspend fun saveShop(shop: Shop): Shop?


}
