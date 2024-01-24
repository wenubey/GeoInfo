package com.wenubey.geoinfo.ui.country.detail

import com.wenubey.geoinfo.domain.model.Country

data class CountryDataState(
    val country: Country? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

