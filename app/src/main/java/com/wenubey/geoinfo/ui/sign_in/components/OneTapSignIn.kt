package com.wenubey.geoinfo.ui.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.wenubey.geoinfo.ui.sign_in.SignInViewModel
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.Utils.Companion.printLog
import com.wenubey.geoinfo.utils.components.ProgressBar

@Composable
fun OneTapSignIn(
    viewModel: SignInViewModel,
    launch: (result: BeginSignInResult) -> Unit
) {
    when(val result = viewModel.oneTapSignInResponse) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> result.data?.let {
            LaunchedEffect(it) {
                launch(it)
            }
        }
        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                printLog(error)
            }
        }
    }

}