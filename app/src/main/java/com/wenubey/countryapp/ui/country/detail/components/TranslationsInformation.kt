package com.wenubey.countryapp.ui.country.detail.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.utils.Constants

@Composable
fun TranslationsInformation(
    country: Country?,
    languages: Map<String, String>,
) {

    var isExpanded by remember { mutableStateOf(false) }
    val mutableInteractionSource = MutableInteractionSource()
    var isExpandedIcon by remember {
        mutableStateOf(Icons.Default.ArrowDownward)
    }


    fun toggleExpansion() {
        isExpanded = !isExpanded
        isExpandedIcon = if (isExpanded) {
            Icons.Default.ArrowBack
        } else {
            Icons.Default.ArrowDownward
        }
    }

    Log.i(Constants.TAG, "isExpanded: $isExpanded")
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = mutableInteractionSource,
                indication = null,
                onClick = {
                    toggleExpansion()
                },
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TranslationsHeader(isExpandedIcon = isExpandedIcon, onClick = { toggleExpansion() })
        val translationsToShow = country?.translations?.filter {
            languages[it.key] != null
        }?.toList()
            .let {
                if (isExpanded) it else it?.take(3)
            }?.toMap()

        translationsToShow?.forEach { translation ->
            Column(
                modifier = Modifier.padding(horizontal = 36.dp, vertical = 4.dp)
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
                    text = "Official: ${translation.value?.official}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Common: ${translation.value?.common}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
}


@Composable
fun TranslationsHeader(
    isExpandedIcon: ImageVector,
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
                .align(Alignment.CenterStart),
            imageVector = isExpandedIcon,
            contentDescription = Constants.COUNTRY_TRANSLATIONS_EXPANDED_CONTENT_DESCRIPTION
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 16.dp).align(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = Constants.COUNTRY_TRANSLATIONS_CONTENT_DESCRIPTION
            )
            Text(
                text = Constants.TRANSLATIONS,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }

}
