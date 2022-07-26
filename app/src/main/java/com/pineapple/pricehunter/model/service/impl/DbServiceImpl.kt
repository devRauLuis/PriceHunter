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
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pineapple.pricehunter.model.Product
import com.pineapple.pricehunter.model.service.DbService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DbServiceImpl @Inject constructor() : DbService {
    override suspend fun getAllProducts(query: String?): List<Product> {
        val q = Firebase.firestore.collection(PRODUCTS_COLLECTION).limit(100).orderBy(
            "name", Query.Direction.ASCENDING
        )
        // If the user is searching for an specific string add case to query
        if (!query.isNullOrEmpty()) q.whereEqualTo("name", query)
        val result = q.get().await()

        // Map result from Firestore JSON to model
        val mappedResult = result.map { it.toObject<Product>().copy(id = it.id) }
        Log.d(TAG, mappedResult.toString())
        return mappedResult
    }

    companion object {
        private const val PRODUCTS_COLLECTION = "Products"
        private const val USER_ID = "userId"
        private const val TAG = "STORAGE SERVICE"
    }


}