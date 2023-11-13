package com.wenubey.countryapp.ui.map.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.wenubey.countryapp.utils.Constants.MAP_SCREEN_TITLE
import com.wenubey.countryapp.utils.Constants.PROFILE_PHOTO_DESCRIPTION

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenTopBar(
    navigateToProfileScreen: () -> Unit,
    profileImage: String? = null,
) {

    TopAppBar(
        title = {
            Text(text = MAP_SCREEN_TITLE)
        },
        actions = {
            AsyncImage(
                model = profileImage,
                modifier = Modifier.clickable {
                    navigateToProfileScreen()
                }.clip(MaterialTheme.shapes.small),
                contentDescription = PROFILE_PHOTO_DESCRIPTION,
                error = rememberVectorPainter(image = Icons.Default.AccountCircle)
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
fun MapScreenTopBarPreview() {
    MapScreenTopBar({})
}