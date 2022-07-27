package com.pineapple.pricehunter.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pineapple.pricehunter.common.snackbar.SnackbarManager
import com.pineapple.pricehunter.common.snackbar.SnackbarMessage
import com.pineapple.pricehunter.common.utils.LoadingState
import com.pineapple.pricehunter.model.Price
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : PriceHunterViewModel() {
    var isUserAuthenticated by mutableStateOf<Boolean>(false)
        private set

    init {
        viewModelScope.launch {
            Firebase.auth.addAuthStateListener {
                isUserAuthenticated = it.currentUser != null
            }
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                Firebase.auth.signInWithEmailAndPassword(email, password).await()
                loadingState.emit(LoadingState.LOADED)
            } catch (e: Exception) {
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }
        }
    }

    fun signWithCredential(credential: AuthCredential, restartApp: () -> Unit) =
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                Firebase.auth.signInWithCredential(credential).await()
                Log.d(
                    "USER", Firebase.auth.currentUser?.email.toString()
                )
                loadingState.emit(LoadingState.LOADED)
                restartApp()
            } catch (e: Exception) {
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }
        }
}