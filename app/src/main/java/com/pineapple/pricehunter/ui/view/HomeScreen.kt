package com.pineapple.pricehunter.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(navigate: (String) -> Unit) {
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
                .fillMaxWidth()
        ) {
            if (Firebase.auth.currentUser == null)
                Column() {
                    Text(
                        "Siempre el mejor precio",
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center
                    )
                    Box(modifier = Modifier.padding(top = 20.dp)) {
                        GoogleSignInButton()
                    }
                }
            else
                Row() {
                    Text("Hi, ")
                    Text(
                        "${Firebase.auth.currentUser!!.displayName}",
                        fontWeight = FontWeight.SemiBold
                    )
                }
        }
    }
}