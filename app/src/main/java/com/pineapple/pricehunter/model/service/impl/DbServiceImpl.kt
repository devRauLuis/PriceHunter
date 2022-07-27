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

package com.pineapple.pricehunter.model.service.impl

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pineapple.pricehunter.model.Product
import com.pineapple.pricehunter.model.Shop
import com.pineapple.pricehunter.model.service.DbService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DbServiceImpl @Inject constructor() : DbService {
    override suspend fun getAllProducts(query: String?): List<Product> {
        val q = Firebase.firestore.collection(PRODUCTS_COLLECTION).orderBy(
            "name", Query.Direction.ASCENDING
        )

        val result = q.get().await()
        var mappedResult = result?.map { it.toObject<Product>().copy(id = it.id) }
        if (!query.isNullOrBlank())
            mappedResult = mappedResult?.filter { it.name.contains(query) }


        // Map result from Firestore JSON to model
        Log.d(TAG, "getAllProducts(${query}): $mappedResult")
        return mappedResult ?: listOf()
    }

    override suspend fun getProduct(id: String): Product? {
        val q = Firebase.firestore.collection(PRODUCTS_COLLECTION).document(id)
        val result = q.get().await()
        val mappedResult = result.toObject<Product>()?.copy(id = id)
        Log.d(TAG, "getProduct: ${mappedResult.toString()}")
        return mappedResult
    }

//    override suspend fun saveProduct(product: Product): Product? {
//        return if (product.id.isNullOrBlank()) createProduct(product) else updateProduct(product)
//    }

    override suspend fun updateProduct(product: Product): Product? {
        Log.d(TAG, "updateProduct: ${product.toString()}")
        val productToUpdate =
            product.copy(updatedAt = Timestamp.now(), updatedBy = Firebase.auth.currentUser?.uid)

        Firebase.firestore.collection(PRODUCTS_COLLECTION)
            .document(productToUpdate.id).set(productToUpdate).await()
        val mappedResult = getProduct(productToUpdate.id)?.let { return it }
        Log.d(TAG, "saveProduct: ${mappedResult.toString()}")
        return mappedResult
    }

    override suspend fun createProduct(product: Product): Product? {
        val productToCreate = product.copy(
            createdAt = Timestamp.now(),
            createdBy = Firebase.auth.currentUser?.uid,
            updatedAt = Timestamp.now(),
            updatedBy = Firebase.auth.currentUser?.uid
        )

        val result = Firebase.firestore.collection(PRODUCTS_COLLECTION).add(productToCreate).await()
        val mappedResult = result.get().await().toObject<Product>()?.copy(id = result.id)

        Log.d(TAG, "saveProduct: ${mappedResult.toString()}")
        return mappedResult
    }

    override suspend fun getAllShops(query: String?): List<Shop> {
        val q = Firebase.firestore.collection(SHOPS_COLLECTION).orderBy(
            "name", Query.Direction.ASCENDING
        )

        val result = q.get().await()
        var mappedResult = result?.map { it.toObject<Shop>().copy(id = it.id) }
        if (!query.isNullOrBlank())
            mappedResult = mappedResult?.filter { it.name.contains(query) }


        // Map result from Firestore JSON to model
        Log.d(TAG, "getAllShops(${query}): $mappedResult")
        return mappedResult ?: listOf()
    }

    override suspend fun getShop(id: String): Shop? {
        val q = Firebase.firestore.collection(SHOPS_COLLECTION).document(id)
        val result = q.get().await()
        val mappedResult = result.toObject<Shop>()?.copy(id = id)
        Log.d(TAG, "getProduct: ${mappedResult.toString()}")
        return mappedResult
    }

    override suspend fun saveShop(shop: Shop): Shop? {
        TODO("Not yet implemented")
    }

    companion object {
        private const val PRODUCTS_COLLECTION = "Products"
        private const val SHOPS_COLLECTION = "Shops"
        private const val USER_ID = "userId"
        private const val TAG = "STORAGE SERVICE"
    }


}