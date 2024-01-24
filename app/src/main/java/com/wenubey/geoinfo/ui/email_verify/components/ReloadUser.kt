package com.wenubey.geoinfo.ui.email_verify.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wenubey.geoinfo.ui.profile.ProfileViewModel
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.Utils.Companion.printLog
import com.wenubey.geoinfo.utils.components.ProgressBar

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