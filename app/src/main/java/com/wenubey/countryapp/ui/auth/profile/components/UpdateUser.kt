package com.wenubey.countryapp.ui.auth.profile.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wenubey.countryapp.ui.auth.profile.ProfileViewModel
import com.wenubey.countryapp.utils.Constants.USER_SUCCESSFULLY_UPDATE_MESSAGE
import com.wenubey.countryapp.utils.Constants.USER_UPDATE_ERROR_MESSAGE
import com.wenubey.countryapp.utils.Resource
import com.wenubey.countryapp.utils.Utils.Companion.printLog
import com.wenubey.countryapp.utils.components.ProgressBar

@Composable
fun UpdateUser(
    viewModel: ProfileViewModel,
    reloadNeed: (Boolean) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    when (val result = viewModel.updateUserResponse) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isUserUpdated = result.data
            LaunchedEffect(isUserUpdated) {
                if (isUserUpdated!!) {
                    reloadNeed(true)
                    snackBarHostState.showSnackbar(USER_SUCCESSFULLY_UPDATE_MESSAGE)
                } else {
                    reloadNeed(false)

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