package com.wenubey.countryapp.domain.repository

import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.utils.CountryListOptions
import com.wenubey.countryapp.utils.DataResponse

interface CountryRepository {

    suspend fun getAllCountries(
        fetchFromRemote: Boolean,
        options: CountryListOptions = CountryListOptions.Default
    ): DataResponse<List<Country>>

    suspend fun getCountry(fetchFromRemote: Boolean = false, countryName: String, countryCode: String): DataResponse<Country>

    suspend fun getCountryCodeFromCache(): Result<Map<String?, String?>>

    suspend fun getLanguages(): Result<Map<String, String>>

    suspend fun updateFavCountry(country: Country, isFavorite: Boolean)

}