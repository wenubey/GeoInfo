package com.wenubey.countryapp.ui.forgot_password

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.wenubey.countryapp.ui.forgot_password.compoents.CantChangePasswordContent
import com.wenubey.countryapp.ui.forgot_password.compoents.ForgotPassword
import com.wenubey.countryapp.ui.forgot_password.compoents.ForgotPasswordContent
import com.wenubey.countryapp.ui.forgot_password.compoents.ForgotPasswordTopBar
import com.wenubey.countryapp.utils.AuthProvider
import com.wenubey.countryapp.utils.Constants.RESET_PASSWORD_MESSAGE
import com.wenubey.countryapp.utils.Utils.Companion.makeToast
import org.koin.androidx.compose.getViewModel

@Composable
fun ForgotPasswordScreen(
    navigateBack: () -> Unit,
    email: String?,
) {
    val viewModel: ForgotPasswordViewModel = getViewModel()
    val context = LocalContext.current

    val currentUserAuthProvider = viewModel.currentUserAuthProvider

    Scaffold(
        topBar = {
            ForgotPasswordTopBar(
                navigateBack = navigateBack
            )
        },
        content = { paddingValues ->
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
                    CantChangePasswordContent(
                        paddingValues = paddingValues
                    )
                }
            }

        },
    )

    ForgotPassword(
        navigateBack = navigateBack,
        showErrorMessage = { error -> context.makeToast(error) },
        showResetPasswordMessage = { context.makeToast(RESET_PASSWORD_MESSAGE) }
    )
}