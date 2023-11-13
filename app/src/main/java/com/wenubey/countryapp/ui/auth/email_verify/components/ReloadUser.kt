package com.wenubey.countryapp.ui.auth.email_verify.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wenubey.countryapp.ui.auth.profile.ProfileViewModel
import com.wenubey.countryapp.utils.Resource
import com.wenubey.countryapp.utils.Utils.Companion.printLog
import com.wenubey.countryapp.utils.components.ProgressBar

@Composable
fun ReloadUser(
    viewModel: ProfileViewModel,
    navigateToProfileScreen: () -> Unit,
) {

    when (val result = viewModel.reloadUserResponse) {
        is Resource.Loading -> ProgressBar()
        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                printLog(error)
            }
        }

        is Resource.Success -> {
            val isUserReloaded = result.data
            LaunchedEffect(isUserReloaded) {
                if (isUserReloaded!!) {
                    navigateToProfileScreen()
                }
            }
        }
    }

}