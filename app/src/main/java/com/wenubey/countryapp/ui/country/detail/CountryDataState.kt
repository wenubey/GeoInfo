package com.wenubey.countryapp.ui.country.detail

import com.wenubey.countryapp.domain.model.Country

data class CountryDataState(
    val country: Country? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
