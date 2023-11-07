package com.wenubey.countryapp.ui.country


sealed class CountryListEvent {
    object Refresh: CountryListEvent()

    data class OnSearchQueryChange(val query: String): CountryListEvent()
}
