package com.wenubey.countryapp.domain.repository

import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.domain.model.History
import com.wenubey.countryapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CountryRepository {

    suspend fun getAllCountries(fetchFromRemote: Boolean): Flow<Resource<List<Country>>>

    suspend fun getCountry(fetchFromRemote: Boolean, countryName: String): Flow<Resource<Country>>
}