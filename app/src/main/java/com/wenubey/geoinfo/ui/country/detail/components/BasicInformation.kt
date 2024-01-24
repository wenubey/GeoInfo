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
import com.wenubey.geoinfo.utils.Constants.UNDEFINED
import com.wenubey.geoinfo.utils.formatWithCommasForPopulation

@Composable
fun BasicInformation(country: Country) {
    InfoHeader(header = stringResource(id = R.string.BASIC_INFORMATION))
    CountryInfoRow(
        header = stringResource(id = R.string.KNOWN_AS),
        imageVector = Icons.Default.TravelExplore,
        contentDescription = stringResource(id = R.string.COUNTRY_KNOWN_AS_CONTENT_DESCRIPTION),
        content = "${country.countryCommonName}\n${country.countryNativeName?.values?.first()?.common ?: UNDEFINED}",
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = stringResource(id = R.string.CAPITAL),
        imageVector = Icons.Filled.Castle,
        contentDescription = stringResource(id = R.string.COUNTRY_CAPITAL_CONTENT_DESCRIPTION),
        content = country.capital?.joinToString(", ") ?: UNDEFINED,
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = stringResource(id = R.string.POPULATION),
        imageVector = Icons.Default.People,
        contentDescription = stringResource(id = R.string.POPULATION_CONTENT_DESCRIPTION),
        content = country.population.formatWithCommasForPopulation()
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = stringResource(id = R.string.TOP_LEVEL_DOMAINS),
        imageVector = Icons.Default.Domain,
        contentDescription = stringResource(id = R.string.COUNTRY_TLD_CONTENT_DESCRIPTION),
        content = country.topLevelDomain?.joinToString("\n") ?: UNDEFINED,
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = stringResource(id = R.string.COUNTRY_CODE),
        imageVector = Icons.Default.Tag,
        contentDescription = stringResource(id = R.string.COUNTRY_CODE_CONTENT_DESCRIPTION),
        content = country.countryCodeCCA2 ?: UNDEFINED,
    )
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    CountryInfoRow(
        header = stringResource(id = R.string.PHONE_CODE),
        imageVector = Icons.Default.Call,
        contentDescription = stringResource(id = R.string.COUNTRY_PHONE_CODE_CONTENT_DESCRIPTION),
        content = country.flagEmojiWithPhoneCode.values.first() ?: UNDEFINED,
    )
}
