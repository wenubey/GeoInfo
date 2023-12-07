package com.wenubey.countryapp.ui.country.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.country.detail.InfoHeader
import com.wenubey.countryapp.utils.Constants

@Composable
fun EconomicInformation(country: Country) {
    InfoHeader(content = Constants.ECONOMICAL_INFORMATION)
    CurrencyRow(country = country)
    DemonymsRow(country = country)
}

@Composable
fun CurrencyRow(country: Country?) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Icon(imageVector = Icons.Default.Money, contentDescription = "")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Currencies: ")
            Column(modifier = Modifier.fillMaxHeight()) {
                country?.currency?.forEach {
                    Text(
                        text = "${it.value.name} (${it.key})",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Justify,
                    )
                }
            }
        }
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
}

@Composable
fun DemonymsRow(country: Country) {
    val localConfig = LocalConfiguration.current
    val screenWidth = localConfig.screenWidthDp
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = Constants.COUNTRY_DEMONYMS_CONTENT_DESCRIPTION
                )
                Text(text = Constants.DEMONYMS)
            }
            Column {
                country.demonyms?.forEach { demonym ->
                    Column {
                        Text(text = "${demonym.key}")
                        Divider(
                            modifier = Modifier.size(
                                height = 1.dp,
                                width = (screenWidth / 3).dp
                            )
                        )
                        demonym.value?.forEach { entry ->
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(
                                    imageVector = if (entry.key!!.contains("f")) Icons.Default.Female else Icons.Default.Male,
                                    contentDescription = Constants.COUNTRY_DEMONYMS_GENDER_CONTENT_DESCRIPTION
                                )
                                Text(text = entry.value ?: Constants.UNDEFINED)
                            }
                        }
                    }

                }
            }
        }
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
}

