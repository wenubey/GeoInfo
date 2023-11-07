package com.wenubey.countryapp.ui.country

import com.wenubey.countryapp.domain.model.Country

data class CountryListDataState(
    val data: List<Country>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
