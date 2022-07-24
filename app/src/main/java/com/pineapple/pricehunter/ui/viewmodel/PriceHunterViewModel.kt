package com.pineapple.pricehunter.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.pineapple.pricehunter.common.snackbar.SnackbarManager
import com.pineapple.pricehunter.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler

open class PriceHunterViewModel() : ViewModel() {
        open val showErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }

        open val logErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        }

        open fun onError(error: Throwable) {
            SnackbarManager.showMessage(error.toSnackbarMessage())
        }
}