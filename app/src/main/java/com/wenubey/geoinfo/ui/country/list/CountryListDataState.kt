package com.wenubey.geoinfo.ui.country.list

import com.wenubey.geoinfo.domain.model.Country

data class CountryListDataState(
    val countries: List<Country>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
