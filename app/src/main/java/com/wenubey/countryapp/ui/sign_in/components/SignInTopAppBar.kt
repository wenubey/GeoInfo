package com.wenubey.countryapp.ui.sign_in.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.wenubey.countryapp.utils.Constants.SIGN_IN_SCREEN_TITLE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInTopAppBar() {
    TopAppBar(
        title = {
            Text(text = SIGN_IN_SCREEN_TITLE)
        }
    )
}