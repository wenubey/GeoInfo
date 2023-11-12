package com.wenubey.countryapp.ui.auth.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wenubey.countryapp.ui.auth.sign_up.SignUpViewModel
import com.wenubey.countryapp.utils.Resource
import com.wenubey.countryapp.utils.Utils.Companion.printLog
import com.wenubey.countryapp.utils.components.ProgressBar

@Composable
fun SignUp(
    viewModel: SignUpViewModel,
    sendEmailVerification: () -> Unit,
    showVerifyEmailMessage: () -> Unit
) {
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
                printLog(error)
            }
        }
    }
}