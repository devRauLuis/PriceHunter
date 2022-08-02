package com.pineapple.pricehunter.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pineapple.pricehunter.common.snackbar.SnackbarManager
import com.pineapple.pricehunter.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.pineapple.pricehunter.common.utils.LoadingState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow

open class PriceHunterViewModel() : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)

    open val showErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    open val logErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    }

    open fun onError(error: Throwable) {
        SnackbarManager.showMessage(error.localizedMessage)
    }

}