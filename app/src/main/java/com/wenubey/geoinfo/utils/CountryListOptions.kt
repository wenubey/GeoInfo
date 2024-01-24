package com.wenubey.geoinfo.utils

sealed class CountryListOptions {

    data object Default : CountryListOptions()
    data class Sort(val sortOption: SortOption, val sortOrder: SortOrder = SortOrder.ASC) : CountryListOptions()
    data class Filter(val query: String) : CountryListOptions()
    data class Combined(val sortOption: SortOption, val sortOrder: SortOrder = SortOrder.ASC, val query: String): CountryListOptions()
}

enum class SortOption {
    NAME,    // Sort by country name
    AREA,    // Sort by area
    POPULATION, // Sort by population
    FAV, // Sort by fav
}


enum class SortOrder {
    ASC,
    DESC
}