package com.wenubey.countryapp.domain.repository

import com.wenubey.countryapp.domain.model.Country

interface CountryRepository {

    suspend fun getAllCountries(fetchFromRemote: Boolean): Result<List<Country>>

    suspend fun getSearchedCountries(fetchFromRemote: Boolean, countryName: String): Result<List<Country>>

    suspend fun getCountry(fetchFromRemote: Boolean = false, countryName: String): Result<Country>
}