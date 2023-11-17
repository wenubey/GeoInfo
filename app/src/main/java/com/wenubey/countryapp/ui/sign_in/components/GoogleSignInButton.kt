package com.wenubey.countryapp.ui.sign_in.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wenubey.countryapp.R
import com.wenubey.countryapp.utils.Constants.GOOGLE_SIGN_IN_BUTTON_DESCRIPTION
import com.wenubey.countryapp.utils.Constants.SIGN_IN_WITH_GOOGLE

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Image(painter = painterResource(id = R.drawable.ic_google_logo), contentDescription = GOOGLE_SIGN_IN_BUTTON_DESCRIPTION)
        Text(text = SIGN_IN_WITH_GOOGLE, modifier = Modifier.padding(6.dp), fontSize = 16.sp)
    }
}