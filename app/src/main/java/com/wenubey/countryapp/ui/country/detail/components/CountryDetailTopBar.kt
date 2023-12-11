package com.wenubey.countryapp.ui.country.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailTopBar(
    animation: Boolean,
    country: Country,
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
    model = ImageRequest.Builder(context)
        .data(country.flag?.get("png"))
        .build(),
    placeholder = rememberVectorPainter(image = Icons.Default.Flag),

    )

    AnimatedVisibility(
        visible = animation,
        enter = fadeIn(
            animationSpec = tween(1000)
        ),
        exit = fadeOut(
            animationSpec = tween(1000)
        ),
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier.size(50.dp),
                        painter = painter,
                        contentDescription = Constants.COUNTRY_FLAG_CONTENT_DESCRIPTION
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = country.countryCommonName ?: Constants.UNDEFINED,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = Constants.NAVIGATION_BACK_CONTENT_DESCRIPTION,
                    )
                }
            },
        )
    }
}