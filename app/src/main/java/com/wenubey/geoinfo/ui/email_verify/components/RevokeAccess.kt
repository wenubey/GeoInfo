package com.wenubey.geoinfo.ui.email_verify.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.profile.ProfileViewModel
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.Utils.Companion.makeToast
import com.wenubey.geoinfo.utils.Utils.Companion.printLog
import com.wenubey.geoinfo.utils.components.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
) {
    val context = LocalContext.current
    val signOut = stringResource(id= R.string.sign_out)
    fun showRevokeAccessMessage() = coroutineScope.launch {
        val result = snackBarHostState.showSnackbar(
            message = context.getString(R.string.access_revoked_message),
            actionLabel = signOut,
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
                    context.makeToast(context.getString(R.string.access_revoked_message))
                }
            }
        }

        is Resource.Error -> result.apply {
            LaunchedEffect(error) {
                printLog(error)
                if (error.message == context.getString(R.string.sensitive_operation_message)) {
                    showRevokeAccessMessage()
                }
            }
        }
    }
}
