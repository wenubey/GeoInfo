package com.wenubey.geoinfo.ui.profile.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wenubey.geoinfo.ui.profile.ProfileViewModel
import com.wenubey.geoinfo.utils.Constants.USER_SUCCESSFULLY_UPDATE_MESSAGE
import com.wenubey.geoinfo.utils.Constants.USER_UPDATE_ERROR_MESSAGE
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.Utils.Companion.printLog
import com.wenubey.geoinfo.utils.components.ProgressBar

@Composable
fun UpdateUser(
    viewModel: ProfileViewModel,
    snackBarHostState: SnackbarHostState
) {
    when (val result = viewModel.updateUserResponse) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isUserUpdated = result.data
            LaunchedEffect(isUserUpdated) {
                if (isUserUpdated!!) {
                    viewModel.getUserData()
                    snackBarHostState.showSnackbar(USER_SUCCESSFULLY_UPDATE_MESSAGE)
                }
            }
        }

        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                printLog(error)
                snackBarHostState.showSnackbar(USER_UPDATE_ERROR_MESSAGE + error.localizedMessage)
            }
        }
    }
}