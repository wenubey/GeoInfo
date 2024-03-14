package com.wenubey.geoinfo.ui.country.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.domain.model.Country
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.fakeCountry


@Composable
fun TranslationsInformation(
    country: Country?  = fakeCountry,
    languages: Map<String, String>  = fakeCountry.language
) {

    var isExpanded by remember { mutableStateOf(false) }
    val mutableInteractionSource = MutableInteractionSource()
    var isExpandedIcon by remember {
        mutableStateOf(Icons.Default.KeyboardArrowDown)
    }

    fun toggleExpansion() {
        isExpanded = !isExpanded
        isExpandedIcon = if (isExpanded) {
            Icons.Default.KeyboardArrowUp
        } else {
            Icons.Default.KeyboardArrowDown
        }
    }
    Column(
        modifier = Modifier.run {
            testTag(stringResource(R.string.translation_information_column_test_tag))
                .clickable(
                    interactionSource = mutableInteractionSource,
                    indication = null,
                    onClick = {
                        toggleExpansion()
                    },
                )
        },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TranslationHeader(isExpandedIcon = isExpandedIcon, onClick = { toggleExpansion() })
        TranslationContent(country = country, languages = languages, isExpanded = isExpanded)
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
}

@Composable
fun TranslationContent(
    country: Country? = fakeCountry,
    languages: Map<String, String> = fakeCountry.language,
    isExpanded: Boolean = false,
) {
    val translationsToShow = country?.translations?.filter {
        languages[it.key] != null
    }?.toList()
        .let {
            if (isExpanded) it else it?.take(3)
        }?.toMap()



    translationsToShow?.forEach { translation ->
        Column(
            modifier = Modifier
                .testTag(
                    stringResource(
                        R.string.translation_content_column_test_tag,
                        translation.key ?: ""
                    )
                )
                .padding(horizontal = 36.dp, vertical = 4.dp)
        ) {
            Text(
                text = "${languages[translation.key]}",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.official, translation.value?.official ?: ""),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.common, translation.value?.common ?: ""),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun TranslationHeader(
    isExpandedIcon: ImageVector = Icons.Default.KeyboardArrowDown,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 4.dp)
                .clickable { onClick() }
                .align(Alignment.CenterEnd),
            imageVector = isExpandedIcon,
            contentDescription = stringResource(id= R.string.country_translations_expanded_content_description)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .testTag(stringResource(R.string.translation_header_row_test_tag))
                .padding(end = 16.dp)
                .align(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = stringResource(id= R.string.country_translations_content_description)
            )
            Text(
                text = stringResource(id= R.string.translations),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun InfoContentPreview() {
     GeoInfoAppTheme {
        Surface {
            TranslationsInformation()
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun TranslationContentPreview() {
     GeoInfoAppTheme {
        Surface {
             TranslationContent()
        }
    }
}