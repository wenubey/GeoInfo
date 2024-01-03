package com.wenubey.countryapp.ui.country.detail.components

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
import com.wenubey.countryapp.R
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants.UNDEFINED
import com.wenubey.countryapp.utils.fakeCountry

@Composable
fun EconomicInformation(country: Country) {
    InfoHeader(header = stringResource(id = R.string.ECONOMICAL_INFORMATION))
    CurrencyRow(country = country)
    DemonymsRow(country = country)
}

@Composable
private fun CurrencyRow(country: Country? = fakeCountry) {
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
                text = stringResource(id = R.string.CURRENCIES),
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
private fun DemonymsRow(country: Country = fakeCountry) {
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
                    contentDescription = stringResource(id = R.string.COUNTRY_DEMONYMS_CONTENT_DESCRIPTION)
                )
                Text(
                    text = stringResource(id = R.string.DEMONYMS),
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
                                    contentDescription = stringResource(id = R.string.COUNTRY_DEMONYMS_GENDER_CONTENT_DESCRIPTION)
                                )
                                Text(
                                    text = entry.value ?: UNDEFINED,
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
    CountryAppTheme {
        Surface {
            CurrencyRow()
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun DemonymsRowPreview() {
    CountryAppTheme {
        Surface {
            DemonymsRow()
        }
    }
}

