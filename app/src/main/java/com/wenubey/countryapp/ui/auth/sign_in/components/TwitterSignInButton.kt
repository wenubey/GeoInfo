package com.wenubey.countryapp.ui.auth.sign_in.components

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
import com.wenubey.countryapp.utils.Constants.SIGN_UP_WITH_TWITTER
import com.wenubey.countryapp.utils.Constants.TWITTER_SIGN_UP_BUTTON_DESCRIPTION

@Composable
fun TwitterSignInButton(
    twitterSingUpClicked: () -> Unit
) {
    Button(onClick = twitterSingUpClicked) {
        Image(painter = painterResource(id = R.drawable.ic_twitter_logo), contentDescription = TWITTER_SIGN_UP_BUTTON_DESCRIPTION)
        Text(text = SIGN_UP_WITH_TWITTER, modifier = Modifier.padding(6.dp), fontSize = 16.sp)
    }
}