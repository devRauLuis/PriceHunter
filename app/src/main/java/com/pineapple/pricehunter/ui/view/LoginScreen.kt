package com.pineapple.pricehunter.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.pineapple.pricehunter.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState

    Column() {
        TextField(
            value = uiState.email ?: "",
            onValueChange = viewModel::onEmailChange,
            placeholder = { Text("Email") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
        )
        TextField(
            value = uiState.password ?: "",
            onValueChange = viewModel::onPasswordChange,
            placeholder = { Text("Password") }, leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Key,
                    contentDescription = "Password"
                )
            }
        )
        Button(onClick = { /*TODO*/ }) {
            Text("Sign in with Google")
        }

    }
}