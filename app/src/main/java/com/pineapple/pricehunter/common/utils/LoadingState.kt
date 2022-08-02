package com.pineapple.pricehunter.common.utils

import com.pineapple.pricehunter.common.snackbar.SnackbarManager

data class LoadingState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?): LoadingState {
            SnackbarManager.showMessage(msg ?: "")
            return LoadingState(Status.FAILED)
        }
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED,
        IDLE,
    }
}