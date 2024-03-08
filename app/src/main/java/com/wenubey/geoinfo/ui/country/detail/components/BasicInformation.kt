package com.wenubey.geoinfo.ui.country.detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.domain.model.Country
import com.wenubey.geoinfo.utils.formatWithCommasForPopulation

@Composable
fun BasicInformation(country: Country) {
    InfoHeader(header = stringResource(id = R.string.basic_information))
    CountryInfoRow(
        header = stringResource(id = R.string.known_as),
        imageVector = Icons.Default.TravelExplore,
        contentDescription = stringResource(id = R.string.country_known_as_content_description),
        content = "${country.countryCommonName}\n${country.countryNativeName?.values?.first()?.common ?: stringResource(id = R.string.undefined)}",
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = stringResource(id = R.string.capital),
        imageVector = Icons.Filled.Castle,
        contentDescription = stringResource(id = R.string.country_capital_content_description),
        content = country.capital?.joinToString(", ") ?: stringResource(id = R.string.undefined),
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = stringResource(id = R.string.population),
        imageVector = Icons.Default.People,
        contentDescription = stringResource(id = R.string.population_content_description),
        content = country.population.formatWithCommasForPopulation()
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = stringResource(id = R.string.top_level_domains),
        imageVector = Icons.Default.Domain,
        contentDescription = stringResource(id = R.string.country_tld_content_description),
        content = country.topLevelDomain?.joinToString("\n") ?: stringResource(id = R.string.undefined),
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = stringResource(id = R.string.country_code),
        imageVector = Icons.Default.Tag,
        contentDescription = stringResource(id = R.string.country_code_content_description),
        content = country.countryCodeCCA2 ?: stringResource(id = R.string.undefined),
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = stringResource(id = R.string.phone_code),
        imageVector = Icons.Default.Call,
        contentDescription = stringResource(id = R.string.country_phone_code_content_description),
        content = country.flagEmojiWithPhoneCode.values.first() ?: stringResource(id = R.string.undefined),
    )
}
