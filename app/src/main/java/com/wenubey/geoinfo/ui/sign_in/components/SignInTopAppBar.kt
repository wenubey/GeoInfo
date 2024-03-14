package com.wenubey.geoinfo.ui.sign_in.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wenubey.geoinfo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInTopAppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.sign_in_screen_title), style = MaterialTheme.typography.bodyMedium)
        }
    )
}