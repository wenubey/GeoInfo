package com.wenubey.countryapp.domain.repository

import com.wenubey.countryapp.domain.model.Country

interface CountryFavRepository {

    suspend fun addCountryToFavourite(country: Country) : Result<String>

    suspend fun deleteCountryFromFavourite(country: Country) : Result<String>

    suspend fun clearAllCountriesFromFavourite(): Result<String>

    suspend fun getAllFavouriteCountries(): Result<List<Country>>
}