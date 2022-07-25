package com.pineapple.pricehunter.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(navigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       
        Box(modifier = Modifier.padding(top = 15.dp)) {
            if (Firebase.auth.currentUser == null)
                GoogleSignInButton()
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