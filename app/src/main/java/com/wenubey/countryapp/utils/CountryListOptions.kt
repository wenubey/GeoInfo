package com.wenubey.countryapp.utils

sealed class CountryListOptions {

    object Default : CountryListOptions()
    data class Sort(val sortOption: SortOption, val sortOrder: SortOrder = SortOrder.ASC) : CountryListOptions()
    data class Filter(val query: String) : CountryListOptions()

    data class Combined(val sortOption: SortOption, val sortOrder: SortOrder = SortOrder.ASC, val query: String): CountryListOptions()
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