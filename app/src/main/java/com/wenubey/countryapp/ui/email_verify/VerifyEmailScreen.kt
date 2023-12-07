package com.wenubey.countryapp.ui.email_verify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.wenubey.countryapp.ui.email_verify.components.ReloadUser
import com.wenubey.countryapp.ui.email_verify.components.RevokeAccess
import com.wenubey.countryapp.ui.email_verify.components.VerifyEmailContent
import com.wenubey.countryapp.ui.profile.ProfileViewModel
import com.wenubey.countryapp.ui.profile.components.ProfileTopBar
import com.wenubey.countryapp.utils.Constants.EMAIL_NOT_VERIFIED_MESSAGE
import com.wenubey.countryapp.utils.Constants.VERIFY_EMAIL_SCREEN_TITLE
import com.wenubey.countryapp.utils.Utils.Companion.makeToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerifyEmailScreen(
    navigateToProfileScreen: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        topBar = {
            ProfileTopBar(
                title = VERIFY_EMAIL_SCREEN_TITLE,
                signOut = { viewModel.signOut() },
                revokeAccess = { viewModel.revokeAccess() },
                navigateBack = navigateBack,
            )
        },
        content = { paddingValues ->
            VerifyEmailContent(
                paddingValues = paddingValues,
                reloadUser = {
                    viewModel.reloadUser()
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    )

    ReloadUser(
        viewModel = viewModel,
        navigateToProfileScreen = {
            if (viewModel.isEmailVerified) {
                navigateToProfileScreen()
            } else {
                context.makeToast(EMAIL_NOT_VERIFIED_MESSAGE)
            }
        },
    )

    RevokeAccess(
        viewModel = viewModel,
        snackBarHostState = snackBarHostState,
        coroutineScope = coroutineScope,
    )
}