package com.wenubey.countryapp.ui.country.list

import com.wenubey.countryapp.utils.CountryListOptions
import com.wenubey.countryapp.utils.SortOption
import com.wenubey.countryapp.utils.SortOrder


sealed class CountryEvent {
    object Refresh: CountryEvent()

    data class OnSearchQueryChange(val query: String): CountryEvent()

    data class OnGetAllCountries(val options: CountryListOptions) : CountryEvent()

    data class OnSortButtonClick(val sortOption: SortOption, val sortOrder: SortOrder) : CountryEvent()

    data class OnGetAllCountriesFilteredAndSorted(val query: String, val sortOrder: SortOrder, val sortOption: SortOption): CountryEvent()

    data class OnGetCountry(val query: String): CountryEvent()

}
