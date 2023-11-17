package com.wenubey.countryapp.ui.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wenubey.countryapp.ui.sign_in.SignInViewModel
import com.wenubey.countryapp.utils.Resource
import com.wenubey.countryapp.utils.Utils.Companion.printLog
import com.wenubey.countryapp.utils.components.ProgressBar

@Composable
fun SignInWithGoogle(
    viewModel: SignInViewModel,
    navigateToProfileScreen: (isSigned: Boolean) -> Unit
) {
    when(val result = viewModel.isUserSigned) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> result.data?.let{ isSigned ->
            LaunchedEffect(isSigned) {
                navigateToProfileScreen(isSigned)
            }
        }
        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                printLog(error)
            }
        }
    }
}