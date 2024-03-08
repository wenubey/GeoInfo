package com.wenubey.geoinfo.ui.sign_up


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.sign_up.components.SendEmailVerification
import com.wenubey.geoinfo.ui.sign_up.components.SignUp
import com.wenubey.geoinfo.ui.sign_up.components.SignUpContent
import com.wenubey.geoinfo.ui.sign_up.components.SignUpTopBar
import com.wenubey.geoinfo.utils.Utils.Companion.makeToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    navigateToSignInScreen: () -> Unit,
    signUpViewModel: SignUpViewModel = koinViewModel()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SignUpTopBar(navigateToSignInScreen = navigateToSignInScreen)
        },
        content = { paddingValues ->
            SignUpContent(
                paddingValues = paddingValues,
                signUp = { email, password ->
                    signUpViewModel.signUpWithEmailAndPassword(email, password)
                },
                navigateToSignInScreen = navigateToSignInScreen
            )
        }
    )

    SignUp(
        sendEmailVerification = { signUpViewModel.sendEmailVerification() },
        showVerifyEmailMessage = { context.makeToast(context.getString(R.string.verify_email_message)) },
        viewModel = signUpViewModel
    )

    SendEmailVerification(signUpViewModel)

}