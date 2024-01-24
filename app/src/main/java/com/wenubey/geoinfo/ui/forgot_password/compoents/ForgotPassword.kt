package com.wenubey.geoinfo.ui.forgot_password.compoents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wenubey.geoinfo.ui.forgot_password.ForgotPasswordViewModel
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.Utils.Companion.printLog
import com.wenubey.geoinfo.utils.components.ProgressBar
import org.koin.androidx.compose.getViewModel

@Composable
fun ForgotPassword(
    navigateBack: () -> Unit,
    showResetPasswordMessage: () -> Unit,
    showErrorMessage: (error: String?) -> Unit,
) {
    val viewModel: ForgotPasswordViewModel = getViewModel()

    when(val result = viewModel.isSentPasswordReset) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isResetEmailSent = result.data
            LaunchedEffect(isResetEmailSent) {
                if (isResetEmailSent!!) {
                    navigateBack()
                    showResetPasswordMessage()
                }
            }
        }
        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                printLog(error)
                showErrorMessage(error.message)
            }
        }
    }

}