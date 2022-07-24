package com.pineapple.pricehunter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.devaruluis.exchanges.ui.theme.PriceHunterTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PriceHunterActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PriceHunterApp()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PriceHunterTheme {
        PriceHunterApp()
    }
}