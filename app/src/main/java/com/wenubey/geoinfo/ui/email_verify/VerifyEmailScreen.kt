package com.wenubey.geoinfo.ui.email_verify

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.email_verify.components.ReloadUser
import com.wenubey.geoinfo.ui.email_verify.components.RevokeAccess
import com.wenubey.geoinfo.ui.email_verify.components.VerifyEmail
import com.wenubey.geoinfo.ui.profile.ProfileViewModel
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.Constants.EMAIL_NOT_VERIFIED_MESSAGE
import com.wenubey.geoinfo.utils.Constants.VERIFY_EMAIL_SCREEN_TITLE
import com.wenubey.geoinfo.utils.Utils.Companion.makeToast
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
                Text(text = VERIFY_EMAIL_SCREEN_TITLE, style = MaterialTheme.typography.bodyMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    IconButton(onClick = { openMenu = !openMenu }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(id= R.string.OPEN_MENU_DESCRIPTION)
                        )
                    }
                }
            }
        },
        actions = {
            DropdownMenu(expanded = openMenu, onDismissRequest = { openMenu = !openMenu }) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id= R.string.SIGN_OUT)) },
                    onClick = {
                        signOut()
                        openMenu = !openMenu
                    },
                )
                DropdownMenuItem(
                    text = { Text(text = stringResource(id= R.string.REVOKE_ACCESS)) },
                    onClick = {
                        revokeAccess()
                        openMenu = !openMenu
                    },
                )
                if (navigateToForgotPasswordScreen != null) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id= R.string.FORGOT_PASSWORD)) },
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
                    contentDescription = stringResource(id= R.string.BACK_BUTTON_DESCRIPTION)
                )
            }
        }
    )
}
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun VerifyEmailContentPreview() {
     GeoInfoAppTheme {
        Surface {
             VerifyEmailContent()
        }
    }
}
