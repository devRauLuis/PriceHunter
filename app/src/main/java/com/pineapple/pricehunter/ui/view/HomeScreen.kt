package com.pineapple.pricehunter.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pineapple.pricehunter.common.utils.LoadingState
import com.pineapple.pricehunter.ui.viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    restartApp: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val currentUser = Firebase.auth.currentUser
    val state by authViewModel.loadingState.collectAsState()

    if (state.status == LoadingState.Status.RUNNING) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp, 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentUser == null)
                Column() {
                    Text(
                        "Siempre el mejor precio",
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center
                    )
                    Box(modifier = Modifier.padding(top = 40.dp)) {
                        GoogleSignInButton(restartApp = {
                            restartApp()
                        })
                    }
                }
            else
                Column() {
                    Row() {
                        Text("Hi, ")
                        Text(
                            "${currentUser!!.displayName}",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Button(
                        onClick = {
                            Firebase.auth.signOut()
                            restartApp()
                        },
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Text("Sign out")
                    }
                }
        }
    }
}