package com.wenubey.countryapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wenubey.countryapp.R
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryAppTheme {
                // A surface container using the 'background' color from the theme
                SignInScreen {
                    Log.i("TAG", "onCreate: Sign In successfully")
                }
            }
        }
    }
}

@Composable
fun SignInScreen(
    navigateToHome: () -> Unit,
) {
    val context = LocalContext.current
    Box(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Rounded.AddCircle, contentDescription = null,
                modifier = Modifier.size(90.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(20.dp))
            SigninButton(
                onSignedIn = navigateToHome,
                onSignInFailed = {
                    Toast.makeText(context,
                        "Try Again",
                        Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
fun SigninButton(
    onSignInFailed: (Exception) -> Unit,
    onSignedIn: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    AndroidView({ context ->
        LoginButton(context).apply {
            val callbackManager = CallbackManager.Factory.create()
            setPermissions("email", "public_profile")
            registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    // do nothing
                }

                override fun onError(error: FacebookException) {
                    onSignInFailed(error)
                }

                override fun onSuccess(result: LoginResult) {
                    scope.launch {
                        val token = result.accessToken.token
                        val credential = FacebookAuthProvider.getCredential(token)
                        val authResult = Firebase.auth.signInWithCredential(credential).await()
                        if (authResult.user != null) {
                            onSignedIn()
                        } else {
                            onSignInFailed(RuntimeException("Could not sign in with Firebase"))
                        }
                    }
                }
            })
        }
    })
}