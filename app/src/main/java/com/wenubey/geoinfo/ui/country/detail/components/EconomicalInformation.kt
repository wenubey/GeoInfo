package com.wenubey.geoinfo.ui.country.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.domain.model.Country
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.fakeCountry

@Composable
fun EconomicInformation(country: Country) {
    CountryInfoHeader(header = stringResource(id = R.string.economical_information))
    CurrencyRow(country = country)
    DemonymsRow(country = country)
}

@Composable
fun CurrencyRow(country: Country? = fakeCountry) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Icon(imageVector = Icons.Default.Money, contentDescription = "")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.currencies),
                style = MaterialTheme.typography.bodyMedium
            )
            Column {
                country?.currency?.forEach {
                    Text(
                        text = "${it.value.name} (${it.key})",
                        style = MaterialTheme.typography.bodyMedium,
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
fun DemonymsRow(country: Country = fakeCountry) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = stringResource(id = R.string.country_demonyms_content_description)
                )
                Text(
                    text = stringResource(id = R.string.demonyms),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column {
                country.demonyms?.forEach { demonym ->
                    Column {
                        Text(text = "${demonym.key}", style = MaterialTheme.typography.bodyMedium)
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
                                    contentDescription = stringResource(id = R.string.country_demonyms_gender_content_description)
                                )
                                Text(
                                    text = entry.value ?: stringResource(id = R.string.undefined),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                }
            }
        }
    }
    Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun CurrencyRowPreview() {
    GeoInfoAppTheme {
        Surface {
            CurrencyRow()
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun DemonymsRowPreview() {
    GeoInfoAppTheme {
        Surface {
            DemonymsRow()
        }
    }
}

