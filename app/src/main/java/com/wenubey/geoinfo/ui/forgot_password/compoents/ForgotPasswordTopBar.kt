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
import com.wenubey.geoinfo.utils.Constants.FORGOT_PASSWORD_SCREEN_TITLE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordTopBar(
    navigateBack: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.testTag(FORGOT_PASSWORD_TOP_BAR_TEST_TAG),
        title = { Text(text = FORGOT_PASSWORD_SCREEN_TITLE, style = MaterialTheme.typography.titleMedium) },
        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .testTag(FORGOT_PASSWORD_TOP_BAR_BUTTON_TEST_TAG),
                onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(id= R.string.BACK_BUTTON_DESCRIPTION)
                )
            }
        },
    )
}

const val FORGOT_PASSWORD_TOP_BAR_TEST_TAG = "forgotPasswordTopBarTest"
const val FORGOT_PASSWORD_TOP_BAR_BUTTON_TEST_TAG = "forgotPasswordTopBarButtonTest"

