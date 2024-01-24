package com.wenubey.geoinfo.ui.country

import com.wenubey.geoinfo.domain.model.Country
import com.wenubey.geoinfo.utils.SortOption
import com.wenubey.geoinfo.utils.SortOrder


sealed class CountryEvent {


    data class OnSearchQueryChange(val query: String): CountryEvent()

    data class OnSortButtonClick(val sortOption: SortOption, val sortOrder: SortOrder) : CountryEvent()

    data class OnGetAllCountriesFilteredAndSorted(val query: String, val sortOrder: SortOrder = SortOrder.ASC, val sortOption: SortOption = SortOption.NAME): CountryEvent()

    data class OnGetCountry(val countryName: String, val countryCode: String): CountryEvent()

    data class OnUserUpdateFavorite(val country: Country, val isFavorite: Boolean): CountryEvent()

    data object OnGetAllFavoriteCountries: CountryEvent()

}
