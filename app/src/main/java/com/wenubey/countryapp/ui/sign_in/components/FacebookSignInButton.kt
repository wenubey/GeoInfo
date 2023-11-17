package com.wenubey.countryapp.ui.sign_in.components

import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.login.widget.LoginButton
import com.wenubey.countryapp.utils.Constants.FACEBOOK_SIGN_UP_BUTTON_DESCRIPTION

@Composable
fun FacebookSignInButton(
    facebookSignInClicked: (View) -> Unit,
    paddingValues: PaddingValues
) {
    AndroidView({ context ->
        LoginButton(context).apply {
            setOnClickListener(facebookSignInClicked)
            contentDescription = FACEBOOK_SIGN_UP_BUTTON_DESCRIPTION
        }
    },
        modifier = Modifier.padding(paddingValues))


}
