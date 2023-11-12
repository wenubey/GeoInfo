package com.wenubey.countryapp.ui.auth.sign_in.components

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.login.widget.LoginButton
import com.wenubey.countryapp.utils.Constants.FACEBOOK_SIGN_UP_BUTTON_DESCRIPTION

@Composable
fun FacebookSignInButton(
    facebookSignUpClicked: (View) -> Unit
) {
    AndroidView({ context ->
        LoginButton(context).apply {
            setOnClickListener(facebookSignUpClicked)
            contentDescription = FACEBOOK_SIGN_UP_BUTTON_DESCRIPTION
        }
    })


}
