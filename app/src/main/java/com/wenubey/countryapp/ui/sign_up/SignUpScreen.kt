package com.wenubey.countryapp.ui.sign_up


import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.wenubey.countryapp.ui.sign_up.components.SendEmailVerification
import com.wenubey.countryapp.ui.sign_up.components.SignUp
import com.wenubey.countryapp.ui.sign_up.components.SignUpContent
import com.wenubey.countryapp.ui.sign_up.components.SignUpTopBar
import com.wenubey.countryapp.utils.Constants.VERIFY_EMAIL_MESSAGE
import com.wenubey.countryapp.utils.Utils.Companion.makeToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    navigateBack: () -> Unit,
    signUpViewModel: SignUpViewModel = koinViewModel()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SignUpTopBar(navigateBack = navigateBack)
        },
        content = { paddingValues ->
            SignUpContent(
                paddingValues = paddingValues,
                signUp = { email, password ->
                    signUpViewModel.signUpWithEmailAndPassword(email, password)
                },
                navigateBack = navigateBack
            )
        }
    )

    SignUp(
        sendEmailVerification = { signUpViewModel.sendEmailVerification() },
        showVerifyEmailMessage = { context.makeToast(VERIFY_EMAIL_MESSAGE) },
        viewModel = signUpViewModel
    )

    SendEmailVerification(signUpViewModel)

}