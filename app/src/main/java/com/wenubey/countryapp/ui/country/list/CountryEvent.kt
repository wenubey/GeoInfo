package com.wenubey.countryapp.ui.country.list

import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.utils.SortOption
import com.wenubey.countryapp.utils.SortOrder


sealed class CountryEvent {
    object Refresh: CountryEvent()

    data class OnSearchQueryChange(val query: String, val isFavorite: Int = 0): CountryEvent()

    data class OnSortButtonClick(val sortOption: SortOption, val sortOrder: SortOrder, val isFavorite: Int = 0) : CountryEvent()

    data class OnGetAllCountriesFilteredAndSorted(val query: String, val sortOrder: SortOrder = SortOrder.ASC, val sortOption: SortOption = SortOption.NAME, val isFavorite: Int = 0): CountryEvent()

    data class OnGetCountry(val countryName: String, val countryCode: String, val isFavorite: Int = 0): CountryEvent()

    data class OnFavoriteClicked(val isFavorite: Int = 0): CountryEvent()

    data class OnUserUpdateFavorite(val country: Country, val isFavorite: Boolean): CountryEvent()

    object OnGetAllFavoriteCountries: CountryEvent()

}
