package com.wenubey.countryapp.ui.sign_in.components

import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wenubey.countryapp.utils.Constants.FORGOT_PASSWORD
import com.wenubey.countryapp.utils.Constants.NO_ACCOUNT
import com.wenubey.countryapp.utils.Constants.SIGN_IN
import com.wenubey.countryapp.utils.components.EmailTextField
import com.wenubey.countryapp.utils.components.PasswordTextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInContent(
    paddingValues: PaddingValues,
    signIn: (email: String, password: String) -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    oneTapSignIn: () -> Unit,
    facebookSignInClicked: (View) -> Unit,
    twitterSignInClicked: () -> Unit,
) {
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailTextField(email = email, onEmailValueChange = { email = it })
        Spacer(modifier = Modifier.height(8.dp))
        PasswordTextField(password = password, onPasswordValueChange = { password = it })
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            keyboard?.hide()
            signIn(email.text, password.text)
        }) {
            Text(text = SIGN_IN, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        GoogleSignInContent(paddingValues = paddingValues, oneTapSignIn = oneTapSignIn)
        Spacer(modifier = Modifier.height(8.dp))
        //FIXME: UI optimization
        FacebookSignInButton(facebookSignInClicked = facebookSignInClicked, paddingValues = paddingValues)
        Spacer(modifier = Modifier.height(8.dp))
        //FIXME: UI optimization
        TwitterSignInButton(twitterSignInClicked = twitterSignInClicked, paddingValues = paddingValues)
        Spacer(modifier = Modifier.height(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.clickable {
                    navigateToForgotPasswordScreen()
                }, text = FORGOT_PASSWORD, fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = NO_ACCOUNT,
                modifier = Modifier.clickable { navigateToSignUpScreen() },
                fontSize = 16.sp
            )
        }
    }
}