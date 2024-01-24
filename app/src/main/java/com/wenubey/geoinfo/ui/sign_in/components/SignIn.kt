package com.wenubey.geoinfo.ui.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wenubey.geoinfo.ui.sign_in.SignInViewModel
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.Utils.Companion.printLog
import com.wenubey.geoinfo.utils.components.ProgressBar

@Composable
fun SignIn(
    viewModel: SignInViewModel,
    showErrorMessage: (error: String?) -> Unit
) {
    when (val result = viewModel.isUserSigned) {
        is Resource.Success -> Unit
        is Resource.Loading ->  ProgressBar()
        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                printLog(error)
                showErrorMessage(error.message)
            }
        }

    }
}