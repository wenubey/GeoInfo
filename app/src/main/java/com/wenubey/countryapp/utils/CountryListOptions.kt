package com.wenubey.countryapp.utils

sealed class CountryListOptions {

    object Default : CountryListOptions()
    data class Sort(val sortOption: SortOption, val sortOrder: SortOrder = SortOrder.ASC, val isFavorite: Int) : CountryListOptions()
    data class Filter(val query: String, val isFavorite: Int) : CountryListOptions()
    data class Favorite(val isFavorite: Int): CountryListOptions()
    data class Combined(val sortOption: SortOption, val sortOrder: SortOrder = SortOrder.ASC, val query: String, val isFavorite: Int): CountryListOptions()
}

enum class SortOption {
    NAME,    // Sort by country name
    AREA,    // Sort by area
    POPULATION // Sort by population
}


enum class SortOrder {
    ASC,
    DESC
}