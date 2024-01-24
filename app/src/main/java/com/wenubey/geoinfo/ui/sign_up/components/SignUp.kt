package com.wenubey.geoinfo.ui.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.wenubey.geoinfo.ui.sign_up.SignUpViewModel
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.Utils.Companion.makeToast
import com.wenubey.geoinfo.utils.Utils.Companion.printLog
import com.wenubey.geoinfo.utils.components.ProgressBar

@Composable
fun SignUp(
    viewModel: SignUpViewModel,
    sendEmailVerification: () -> Unit,
    showVerifyEmailMessage: () -> Unit
) {
    val context = LocalContext.current
    when (val result = viewModel.signUpResponse) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isUserSignedUp = result.data
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp!!) {
                    sendEmailVerification()
                    showVerifyEmailMessage()
                }
            }
        }
        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                context.makeToast(error.message)
                printLog(error)
            }
        }
    }
}