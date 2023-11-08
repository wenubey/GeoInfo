package com.wenubey.countryapp.ui.country.list


sealed class CountryListEvent {
    object Refresh: CountryListEvent()

    data class OnSearchQueryChange(val query: String): CountryListEvent()
}
