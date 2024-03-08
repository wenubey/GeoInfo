package com.wenubey.geoinfo.ui.profile.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.profile.ProfileViewModel
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.Utils.Companion.printLog
import com.wenubey.geoinfo.utils.components.ProgressBar

@Composable
fun UpdateUser(
    viewModel: ProfileViewModel,
    snackBarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    when (val result = viewModel.updateUserResponse) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isUserUpdated = result.data
            LaunchedEffect(isUserUpdated) {
                if (isUserUpdated!!) {
                    viewModel.getUserData()
                    snackBarHostState.showSnackbar(context.getString(R.string.user_successfully_update_message))
                }
            }
        }

        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                printLog(error)
                snackBarHostState.showSnackbar(context.getString(R.string.user_update_error_message) + error.localizedMessage)
            }
        }
    }
}