package com.wenubey.countryapp.ui.email_verify.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.wenubey.countryapp.ui.profile.ProfileViewModel
import com.wenubey.countryapp.utils.Constants.ACCESS_REVOKED_MESSAGE
import com.wenubey.countryapp.utils.Constants.REVOKE_ACCESS_MESSAGE
import com.wenubey.countryapp.utils.Constants.SENSITIVE_OPERATION_MESSAGE
import com.wenubey.countryapp.utils.Resource
import com.wenubey.countryapp.utils.Utils.Companion.makeToast
import com.wenubey.countryapp.utils.Utils.Companion.printLog
import com.wenubey.countryapp.utils.components.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
) {
    val context = LocalContext.current

    fun showRevokeAccessMessage() = coroutineScope.launch {
        val result = snackBarHostState.showSnackbar(
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT,
        )
        if (result == SnackbarResult.ActionPerformed) {
            viewModel.signOut()
        }
    }

    when (val result = viewModel.revokeAccessResponse) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isAccessRevoked = result.data
            LaunchedEffect(isAccessRevoked) {
                if (isAccessRevoked!!) {
                    context.makeToast(ACCESS_REVOKED_MESSAGE)
                }
            }
        }

        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                printLog(error)
                if (error.message == SENSITIVE_OPERATION_MESSAGE) {
                    showRevokeAccessMessage()
                }
            }
        }
    }
}

private const val SIGN_OUT = "Sign out"
