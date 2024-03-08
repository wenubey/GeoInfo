package com.wenubey.geoinfo.ui.forgot_password

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.forgot_password.compoents.ForgotPassword
import com.wenubey.geoinfo.ui.forgot_password.compoents.ForgotPasswordContent
import com.wenubey.geoinfo.ui.forgot_password.compoents.ForgotPasswordTopBar
import com.wenubey.geoinfo.utils.AuthProvider
import com.wenubey.geoinfo.utils.Utils.Companion.makeToast
import com.wenubey.geoinfo.utils.components.ErrorScreen
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
                        ErrorScreen(
                            modifier = Modifier.fillMaxSize(),
                            error = stringResource(id = R.string.cant_change_password),
                        )
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
        showResetPasswordMessage = { context.makeToast(context.getString(R.string.reset_password_message)) }
    )
}
