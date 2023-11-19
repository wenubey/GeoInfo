package com.wenubey.countryapp.ui.map.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.wenubey.countryapp.utils.Constants.MAP_SCREEN_TITLE
import com.wenubey.countryapp.utils.Constants.PROFILE_PHOTO_DESCRIPTION

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenTopBar(
    navigateToProfileScreen: () -> Unit,
    photoUri: Uri?
) {

    val painter = rememberAsyncImagePainter(
        model = photoUri,
        error = rememberVectorPainter(
            image = Icons.Default.AccountCircle
        ),
    )
    TopAppBar(
        title = {
            Text(text = MAP_SCREEN_TITLE)
        },
        actions = {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 4.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        navigateToProfileScreen()
                    },
                painter = painter,
                contentDescription = PROFILE_PHOTO_DESCRIPTION,
            )
        }
    )
}

