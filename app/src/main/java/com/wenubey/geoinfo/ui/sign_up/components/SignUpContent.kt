package com.wenubey.geoinfo.ui.sign_up.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.components.EmailTextField
import com.wenubey.geoinfo.utils.components.PasswordTextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpContent(
    paddingValues: PaddingValues = PaddingValues(),
    signUp: (email: String, password: String) -> Unit = { _, _ -> },
    navigateToSignInScreen: () -> Unit = {}
) {
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    val keyboard = LocalSoftwareKeyboardController.current
    var isButtonEnabled by remember { mutableStateOf(true) }
    val isPasswordVisible: MutableState<Boolean> = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .testTag(SIGN_UP_COLUMN)
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailTextField(
            email = email,
            onEmailValueChange = { newEmail, isError ->
                email = newEmail
                isButtonEnabled = isError
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        PasswordTextField(
            password = password,
            onPasswordValueChange = { newPassword, isError ->
                password = newPassword
                isButtonEnabled = isError
            },
            isPasswordVisible = isPasswordVisible
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.testTag(SIGN_UP_BUTTON),
            onClick = {
                keyboard?.hide()
                signUp(email.text, password.text)
            },
        ) {
            Text(
                text = stringResource(id = R.string.SIGN_UP),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Text(
            modifier = Modifier
                .clickable {
                    navigateToSignInScreen()
                }
                .testTag(NAVIGATE_TO_SIGN_IN_BUTTON),
            text = stringResource(id = R.string.ALREADY_USER),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

const val SIGN_UP_COLUMN = "signUpColumn"
const val SIGN_UP_BUTTON = "signUpButton"
const val NAVIGATE_TO_SIGN_IN_BUTTON = "navigateToSignInButton"


@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun SignUpContentPreview() {
    GeoInfoAppTheme {
        Surface {
            SignUpContent()
        }
    }
}
