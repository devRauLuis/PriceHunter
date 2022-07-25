package com.pineapple.pricehunter.ui.view

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.pineapple.pricehunter.R
import com.pineapple.pricehunter.common.utils.LoadingState
import com.pineapple.pricehunter.ui.theme.googleSmall
import com.pineapple.pricehunter.ui.viewmodel.AuthViewModel
import okhttp3.internal.wait

@Composable
fun GoogleSignInButton(
    restartApp: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {

    // Equivalent of onActivityResult
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                viewModel.signWithCredential(credential, restartApp)
            } catch (e: ApiException) {
                Log.w("TAG", "Google sign in failed", e)
            }
        }


    val context = LocalContext.current
    val token = stringResource(com.pineapple.pricehunter.R.string.default_web_client_id)
    val onGoogleSignInClick = {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        launcher.launch(googleSignInClient.signInIntent)
    }
    ElevatedButton(
//        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onGoogleSignInClick,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        tint = Color.Unspecified,
                        painter = painterResource(id = R.drawable.google_ic),
                        contentDescription = null,
                    )
                    Row(
                        modifier = Modifier.padding(start = 16.dp),
                    ) {
                        Text("Sign in with ")
                        Text(
                            style = googleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            text = "Google"
                        )
                    }
                }
            )
        }
    )

}



