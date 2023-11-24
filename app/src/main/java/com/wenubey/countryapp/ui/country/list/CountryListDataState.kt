package com.wenubey.countryapp.ui.country.list

import com.wenubey.countryapp.domain.model.Country

data class CountryListDataState(
    val countries: List<Country>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
