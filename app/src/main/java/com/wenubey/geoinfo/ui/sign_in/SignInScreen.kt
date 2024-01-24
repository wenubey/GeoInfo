package com.wenubey.geoinfo.ui.sign_in

import android.app.Activity
import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.wenubey.geoinfo.ui.sign_in.components.OneTapSignIn
import com.wenubey.geoinfo.ui.sign_in.components.SignIn
import com.wenubey.geoinfo.ui.sign_in.components.SignInContent
import com.wenubey.geoinfo.ui.sign_in.components.SignInTopAppBar
import com.wenubey.geoinfo.ui.sign_in.components.SignInWithGoogle
import com.wenubey.geoinfo.utils.Utils.Companion.makeToast
import com.wenubey.geoinfo.utils.Utils.Companion.printLog
import org.koin.androidx.compose.koinViewModel


@Composable
fun SignInScreen(
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    navigateToMapScreen: () -> Unit,
    viewModel: SignInViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return

    Scaffold(
        topBar = {
            SignInTopAppBar()
        },
        content = { paddingValues ->
            SignInContent(
                paddingValues = paddingValues,
                signIn = { email, password ->
                    viewModel.signInWithEmail(email, password)
                },
                navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
                navigateToSignUpScreen = navigateToSignUpScreen,
                oneTapSignIn = { viewModel.oneTapSignIn() },
                facebookSignInClicked = { viewModel.signInWithFacebook(activity) },
                twitterSignInClicked = { viewModel.signInWithTwitter(activity) }
            )
        }
    )

    SignIn(showErrorMessage = { error -> context.makeToast(error) }, viewModel = viewModel)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credentials =
                        viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credentials.googleIdToken
                    val googleCredentials = getCredential(googleIdToken, null)
                    viewModel.signInWithGoogle(googleCredentials)
                } catch (it: Exception) {
                    printLog(it)
                }
            }

        }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    OneTapSignIn(launch = { launch(it) }, viewModel = viewModel)

    SignInWithGoogle(navigateToProfileScreen = { isSigned ->
        if (isSigned) {
            navigateToMapScreen()
        }
    }, viewModel = viewModel)


}

