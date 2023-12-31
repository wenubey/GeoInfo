package com.wenubey.countryapp.ui.forgot_password

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.wenubey.countryapp.ui.forgot_password.compoents.ForgotPassword
import com.wenubey.countryapp.ui.forgot_password.compoents.ForgotPasswordContent
import com.wenubey.countryapp.ui.forgot_password.compoents.ForgotPasswordTopBar
import com.wenubey.countryapp.utils.AuthProvider
import com.wenubey.countryapp.utils.Utils.Companion.makeToast
import com.wenubey.countryapp.utils.components.ErrorScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreen(
    navigateBack: () -> Unit,
    email: String?,
    viewModel: ForgotPasswordViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val currentUserAuthProvider = viewModel.currentUserAuthProvider
    val isUserSignedIn = viewModel.isUserSignedIn

    Scaffold(
        topBar = {
            ForgotPasswordTopBar(
                navigateBack = navigateBack
            )
        },
        content = { paddingValues ->
            if (isUserSignedIn) {
                when (currentUserAuthProvider) {
                    AuthProvider.EMAIL -> {
                        ForgotPasswordContent(
                            paddingValues = paddingValues,
                            sendPasswordResetMail = { email ->
                                viewModel.sendPasswordResetEmail(email)
                            },
                            userEmail = email ?: ""
                        )
                    }

                    else -> {
                        ErrorScreen(modifier = Modifier.fillMaxSize(), error = CANT_CHANGE_PASSWORD)
                    }
                }
            } else {
                ForgotPasswordContent(
                    paddingValues = paddingValues,
                    sendPasswordResetMail = { email ->
                        viewModel.sendPasswordResetEmail(email)
                    },
                    userEmail = email ?: ""
                )
            }
        },
    )

    ForgotPassword(
        navigateBack = navigateBack,
        showErrorMessage = { error -> context.makeToast(error) },
        showResetPasswordMessage = { context.makeToast(RESET_PASSWORD_MESSAGE) }
    )
}

private const val CANT_CHANGE_PASSWORD =
    "Sorry, you log in different provider than email, you can not change or forgot your password."
const val RESET_PASSWORD_MESSAGE = "We've sent you an email with a link to reset the password."