package com.wenubey.countryapp.ui.country.detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.utils.Constants

@Composable
fun CulturalInformation(country: Country) {
    InfoHeader(content = Constants.CULTURAL_INFORMATION)
    CountryInfoRow(
        header = Constants.LANGUAGES,
        imageVector = Icons.Default.Translate,
        contentDescription = Constants.COUNTRY_LANGUAGES_CONTENT_DESCRIPTION,
        content = country.language?.values?.joinToString("\n")
            ?: Constants.UNDEFINED
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = Constants.TIMEZONES,
        imageVector = Icons.Default.Alarm,
        contentDescription = Constants.COUNTRY_TIMEZONES_CONTENT_DESCRIPTION,
        content = country.timezones?.joinToString("\n") ?: Constants.UNDEFINED,
    )
}