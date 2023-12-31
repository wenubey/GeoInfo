package com.wenubey.countryapp.ui.email_verify

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.countryapp.ui.email_verify.components.ReloadUser
import com.wenubey.countryapp.ui.email_verify.components.RevokeAccess
import com.wenubey.countryapp.ui.email_verify.components.VerifyEmail
import com.wenubey.countryapp.ui.profile.ProfileViewModel
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Utils.Companion.makeToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerifyEmailScreen(
    navigateToMapScreen: () -> Unit,
    navigateBack: () -> Unit,
) {
    VerifyEmailContent(
        navigateToMapScreen = navigateToMapScreen,
        navigateBack = navigateBack,
    )
}

@Composable
private fun VerifyEmailContent(
    navigateToMapScreen: () -> Unit = {},
    navigateBack: () -> Unit =  {},
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
            VerifyEmailTopBar(
                title = VERIFY_EMAIL_SCREEN_TITLE,
                signOut = { viewModel.signOut() },
                revokeAccess = { viewModel.revokeAccess() },
                navigateBack = navigateBack,
            )
        },
        content = { paddingValues ->
            VerifyEmail(
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
                navigateToMapScreen()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerifyEmailTopBar(
    title: String,
    signOut: () -> Unit,
    revokeAccess: () -> Unit,
    navigateBack: () -> Unit,
    navigateToForgotPasswordScreen: (() -> Unit)? = null
) {
    var openMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    IconButton(onClick = { openMenu = !openMenu }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = OPEN_MENU_DESCRIPTION
                        )
                    }
                }
            }
        },
        actions = {
            DropdownMenu(expanded = openMenu, onDismissRequest = { openMenu = !openMenu }) {
                DropdownMenuItem(
                    text = { Text(text = SIGN_OUT) },
                    onClick = {
                        signOut()
                        openMenu = !openMenu
                    },
                )
                DropdownMenuItem(
                    text = { Text(text = REVOKE_ACCESS) },
                    onClick = {
                        revokeAccess()
                        openMenu = !openMenu
                    },
                )
                if (navigateToForgotPasswordScreen != null) {
                    DropdownMenuItem(
                        text = { Text(text = FORGOT_PASSWORD) },
                        onClick = {
                            navigateToForgotPasswordScreen()
                        },
                    )
                }

            }
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = BACK_BUTTON_DESCRIPTION
                )
            }
        }
    )
}
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun VerifyEmailContentPreview() {
     CountryAppTheme {
        Surface {
             VerifyEmailContent()
        }
    }
}

private const val FORGOT_PASSWORD = "Forgot password?"
private const val REVOKE_ACCESS = "Revoke Access"
private const val SIGN_OUT = "Sign out"
private const val BACK_BUTTON_DESCRIPTION = "Back to previous screen"
private const val EMAIL_NOT_VERIFIED_MESSAGE = "Your email is not verified."
private const val OPEN_MENU_DESCRIPTION = "This buttons opens the menu"
private const val VERIFY_EMAIL_SCREEN_TITLE = "Verify Screen"
