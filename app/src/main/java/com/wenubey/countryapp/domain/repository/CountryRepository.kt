package com.wenubey.countryapp.domain.repository

import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.utils.CountryListOptions

interface CountryRepository {

    suspend fun getAllCountries(
        fetchFromRemote: Boolean,
        options: CountryListOptions = CountryListOptions.Default
    ): Result<List<Country>>

    suspend fun getCountry(fetchFromRemote: Boolean = false, countryName: String): Result<Country>

    suspend fun getCountryCodeFromCache(): Result<Map<String?, String?>>
}