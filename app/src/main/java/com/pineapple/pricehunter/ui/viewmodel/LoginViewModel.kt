package com.pineapple.pricehunter.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.pineapple.pricehunter.auth.AccountService
import com.pineapple.pricehunter.common.ext.isValidEmail
import com.pineapple.pricehunter.common.snackbar.SnackbarManager
import com.pineapple.pricehunter.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.pineapple.pricehunter.R.string as AppText


data class AuthUiState(
    val email: String = "", val password: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    val accountService: AccountService,
    val storageService: StorageService
) :
    PriceHunterViewModel() {
    var uiState by mutableStateOf(AuthUiState())
        private set

    private val email get() = uiState.email
    private val password get() = uiState.password

    fun onEmailChange(newValue: String = "") {
        uiState = uiState.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String = "") {
        uiState = uiState.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        viewModelScope.launch(showErrorExceptionHandler) {
            val oldUserId = accountService.getUserId()
            accountService.authenticate(email, password) { error ->
                if (error == null) {
                    linkWithEmail()
                    updateUserId(oldUserId, openAndPopUp)
                } else onError(error)
            }
        }
    }

    private fun linkWithEmail() {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.linkAccount(email, password) { error ->
            }
        }
    }

    private fun updateUserId(oldUserId: String, openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val newUserId = accountService.getUserId()

            storageService.updateUserId(oldUserId, newUserId) { error ->
//                if (error != null)
//                else openAndPopUp(SETTINGS_SCREEN, LOGIN_SCREEN)
            }
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.sendRecoveryEmail(email) { error ->
                if (error != null) onError(error)
//                else SnackbarManager.showMessage(AppText.recovery_email_sent)
            }
        }
    }
}