package com.wenubey.geoinfo.ui.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wenubey.geoinfo.ui.sign_up.SignUpViewModel
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.Utils.Companion.printLog
import com.wenubey.geoinfo.utils.components.ProgressBar

@Composable
fun SendEmailVerification(
    viewModel: SignUpViewModel,
) {
    when(val result = viewModel.sendEmailVerificationResponse) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> Unit
        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                printLog(error)
            }
        }
    }
}