package com.wenubey.geoinfo.ui.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme

@Composable
fun AccountSettingsMenu(
    modifier: Modifier = Modifier,
    signOut: () -> Unit,
    revokeAccess: () -> Unit,
    navigateToForgotPasswordScreen: (() -> Unit)? = null,
) {
    MenuContent(
        signOut = signOut,
        revokeAccess = revokeAccess,
        navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
        modifier = modifier
    )
}

@Composable
private fun MenuContent(
    modifier: Modifier = Modifier,
    signOut: () -> Unit,
    revokeAccess: () -> Unit,
    navigateToForgotPasswordScreen: (() -> Unit)? = null,
) {
    var openMenu by remember { mutableStateOf(false) }

    IconButton(
        modifier = modifier,
        onClick = { openMenu = !openMenu },
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(id= R.string.PROFILE_SCREEN_ACCOUNT_SETTINGS_CONTENT_DESCRIPTION),
        )
        DropdownMenu(
            expanded = openMenu,
            offset = DpOffset(x = (6).dp, y = (-36).dp),
            onDismissRequest = { openMenu = !openMenu },
        ) {
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
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun MenuContentPreview() {
    GeoInfoAppTheme {
        Surface {
            MenuContent(
                signOut = {},
                revokeAccess = {},
            )
        }
    }
}
