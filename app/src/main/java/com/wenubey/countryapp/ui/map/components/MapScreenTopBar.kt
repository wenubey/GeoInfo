package com.wenubey.countryapp.ui.map.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.countryapp.utils.Constants.MAP_SCREEN_TITLE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenTopBar(
) {
    TopAppBar(
        title = {
            Text(text = MAP_SCREEN_TITLE)
        },
    )
}

@Composable
@Preview(showBackground = true)
fun MapScreenTopBarPreview() {
    MapScreenTopBar()
}