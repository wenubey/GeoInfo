package com.wenubey.geoinfo.utils.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, error: String) {
    ErrorScreenContent(error = error, modifier = modifier)
}

@Composable
private fun ErrorScreenContent(modifier: Modifier = Modifier, error: String = stringResource(id= R.string.preview_error)) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp
    val screenHeight = config.screenHeightDp
    Box(
        modifier = modifier.size(height = (screenHeight / 3).dp, width = (screenWidth / 2).dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f),
            imageVector = Icons.Default.Error,
            contentDescription = stringResource(id= R.string.error_screen_content_description),
            tint = Color.Red.copy(alpha = 0.3f)
        )
        Text(error, style = errorTypography(), textAlign = TextAlign.Center)
    }
}

@Composable
private fun errorTypography(): TextStyle =
    MaterialTheme.typography.titleMedium.copy(color = Color.Red)

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun ErrorScreenContentPreview() {
    GeoInfoAppTheme {
        Surface {
            ErrorScreenContent()
        }
    }
}
