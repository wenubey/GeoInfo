package com.wenubey.countryapp.ui.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wenubey.countryapp.utils.Constants.BACK_BUTTON_DESCRIPTION
import com.wenubey.countryapp.utils.Constants.FORGOT_PASSWORD
import com.wenubey.countryapp.utils.Constants.OPEN_MENU_DESCRIPTION
import com.wenubey.countryapp.utils.Constants.REVOKE_ACCESS
import com.wenubey.countryapp.utils.Constants.SIGN_OUT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
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
                            //TODO user email value send
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