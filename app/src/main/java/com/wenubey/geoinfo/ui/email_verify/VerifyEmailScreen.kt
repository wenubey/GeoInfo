package com.wenubey.geoinfo.ui.email_verify

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.email_verify.components.ReloadUser
import com.wenubey.geoinfo.ui.email_verify.components.RevokeAccess
import com.wenubey.geoinfo.ui.profile.ProfileViewModel
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.Utils.Companion.makeToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerifyEmailScreen(
    navigateToMapScreen: () -> Unit = {},
    navigateBack: () -> Unit = {},
    viewModel: ProfileViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var isMenuOpened by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        topBar = {
            VerifyEmailTopBar(
                signOut = { viewModel.signOut() },
                revokeAccess = { viewModel.revokeAccess() },
                navigateBack = navigateBack,
                isMenuOpened = isMenuOpened,
                onMenuOpenedClicked = {
                    isMenuOpened = !isMenuOpened
                }
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
                navigateToMapScreen()
            } else {
                context.makeToast(context.getString(R.string.email_not_verified_message))
            }
        },
    )

    RevokeAccess(
        viewModel = viewModel,
        snackBarHostState = snackBarHostState,
        coroutineScope = coroutineScope,
    )
}

@Composable
fun VerifyEmailContent(
    paddingValues: PaddingValues = PaddingValues(4.dp),
    reloadUser: () -> Unit = {},
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .testTag(stringResource(id = R.string.verify_email_content_test_tag))
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .semantics {
                    contentDescription = context.getString(R.string.user_reload_request_cd)
                }
                .clickable {
                    reloadUser()
                },
            text = stringResource(id = R.string.already_verified_message),
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(id = R.string.spam_email_message), fontSize = 16.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyEmailTopBar(
    signOut: () -> Unit = {},
    revokeAccess: () -> Unit = {},
    navigateBack: () -> Unit = {},
    navigateToForgotPasswordScreen: (() -> Unit)? = null,
    isMenuOpened: Boolean = false,
    onMenuOpenedClicked: () -> Unit = {},
) {
    val context = LocalContext.current
    TopAppBar(
        modifier = Modifier.testTag(stringResource(id = R.string.verify_email_top_bar_test_tag)),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.verify_screen_title), style = MaterialTheme.typography.bodyMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    IconButton(onClick = onMenuOpenedClicked) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(id = R.string.open_menu_description)
                        )
                    }
                }
            }
        },
        actions = {
            DropdownMenu(
                modifier = Modifier.testTag(stringResource(id = R.string.verify_email_top_bar_menu_test_tag)),
                expanded = isMenuOpened, onDismissRequest = { onMenuOpenedClicked() }) {
                DropdownMenuItem(
                    modifier = Modifier.semantics { contentDescription =  context.getString(R.string.sign_out) },
                    text = { Text(text = stringResource(id = R.string.sign_out)) },
                    onClick = {
                        signOut()
                        onMenuOpenedClicked()
                    },
                )
                DropdownMenuItem(
                    modifier = Modifier.semantics { contentDescription =  context.getString(R.string.revoke_access)},
                    text = { Text(text = stringResource(id = R.string.revoke_access)) },
                    onClick = {
                        revokeAccess()
                        onMenuOpenedClicked()
                    },
                )
                if (navigateToForgotPasswordScreen != null) {
                    DropdownMenuItem(
                        modifier = Modifier.semantics { contentDescription =  context.getString(R.string.forgot_password)},
                        text = { Text(text = stringResource(id = R.string.forgot_password)) },
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
                    contentDescription = stringResource(id = R.string.back_button_description)
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
            VerifyEmailScreen()
        }
    }
}
