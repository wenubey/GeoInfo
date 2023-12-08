package com.wenubey.countryapp.ui.country.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
    fun toggleExpansion() {
        isExpanded = !isExpanded
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = mutableInteractionSource,
                indication = null,
                onClick = {
                    toggleExpansion()
                }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = Constants.COUNTRY_TRANSLATIONS_CONTENT_DESCRIPTION
            )
            Text(
                text = Constants.TRANSLATIONS,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Column {
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
                    Text(text = "official: ${translation.value?.official}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "common: ${translation.value?.common}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
}
