package com.wenubey.geoinfo.ui.forgot_password.compoents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.wenubey.geoinfo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordTopBar(
    navigateBack: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.testTag(stringResource(id = R.string.forgot_password_top_bar_test_tag)),
        title = { Text(text = stringResource(id = R.string.forgot_password_screen_title), style = MaterialTheme.typography.titleMedium) },
        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .testTag(stringResource(id = R.string.forgot_password_top_bar_button_test_tag)),
                onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(id= R.string.back_button_description)
                )
            }
        },
    )
}

