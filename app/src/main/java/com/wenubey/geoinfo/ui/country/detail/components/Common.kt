package com.wenubey.geoinfo.ui.country.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.ui.theme.CountryAppTheme
import com.wenubey.geoinfo.utils.Constants


@Composable
fun CountryInfoRow(
    imageVector: ImageVector = Icons.Default.DeveloperMode,
    contentDescription: String = "",
    content: String = Constants.PREVIEW_CONTENT,
    header: String = Constants.PREVIEW_HEADER
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = header,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Composable
fun InfoHeader(
    header: String = Constants.PREVIEW_HEADER
) {
    Text(
        text = header,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall
    )

}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun CountryInfoRowPreview() {
     CountryAppTheme {
        Surface {
             CountryInfoRow()
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun InfoHeaderPreview() {
     CountryAppTheme {
        Surface {
             InfoHeader()
        }
    }
}