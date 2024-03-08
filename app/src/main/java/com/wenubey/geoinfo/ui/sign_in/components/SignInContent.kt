package com.wenubey.geoinfo.ui.sign_in.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun SignInContent(
    paddingValues: PaddingValues = PaddingValues(4.dp),
    signIn: (email: String, password: String) -> Unit = { _, _ -> },
    navigateToForgotPasswordScreen: () -> Unit = {},
    navigateToSignUpScreen: () -> Unit = {},
    oneTapSignIn: () -> Unit = {},
    facebookSignInClicked: () -> Unit = {},
    twitterSignInClicked: () -> Unit = {},
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
            .testTag(stringResource(id = R.string.sign_in_column_test_tag))
            .padding(paddingValues)
            .fillMaxSize(),
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
            modifier = Modifier.testTag(stringResource(id = R.string.sign_in_button_test_tag)),
            enabled = isButtonEnabled,
            onClick = {
                keyboard?.hide()
                signIn(email.text, password.text)
            },
        ) {
            Text(
                text = stringResource(id = R.string.sign_in),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        GoogleSignInButton(
            modifier = Modifier.fillMaxWidth(0.7f),
            oneTapSignIn = oneTapSignIn,
        )
        Spacer(modifier = Modifier.height(8.dp))
        FacebookSignInButton(
            facebookSignInClicked = facebookSignInClicked,
            modifier = Modifier.fillMaxWidth(0.7f),
        )
        Spacer(modifier = Modifier.height(8.dp))
        XSignInButton(
            modifier = Modifier.fillMaxWidth(0.7f),
            twitterSignInClicked = twitterSignInClicked,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier
                    .clickable {
                        navigateToForgotPasswordScreen()
                    }
                    .testTag(stringResource(id = R.string.forgot_password_button_test_tag)),
                text = stringResource(id= R.string.forgot_password),
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id= R.string.no_account),
                modifier = Modifier
                    .clickable { navigateToSignUpScreen() }
                    .testTag(stringResource(id = R.string.navigate_to_sign_up_button_test_tag)),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}




@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun SignInContentPreview() {
    GeoInfoAppTheme {
        Surface {
            SignInContent(
                paddingValues = PaddingValues(),
                signIn = { _, _ -> },
                navigateToForgotPasswordScreen = { },
                navigateToSignUpScreen = { },
                oneTapSignIn = { },
                facebookSignInClicked = {},
                twitterSignInClicked = {},
            )
        }
    }
}
