package com.wenubey.geoinfo.ui.sign_up.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wenubey.geoinfo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpTopBar(
    navigateToSignInScreen: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.sign_up_screen_title), style = MaterialTheme.typography.bodyMedium)
        },

        navigationIcon = {
            IconButton(onClick = navigateToSignInScreen) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(id= R.string.back_button_description)
                )
            }
        }
    )
}
