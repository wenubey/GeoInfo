package com.wenubey.geoinfo.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.wenubey.geoinfo.domain.model.Country
import com.wenubey.geoinfo.utils.CountryListOptions
import com.wenubey.geoinfo.utils.DataResponse

interface CountryRepository {

    suspend fun getAllCountries(
        fetchFromRemote: Boolean,
        options: CountryListOptions = CountryListOptions.Default
    ): DataResponse<List<Country>>

    suspend fun getCountry(fetchFromRemote: Boolean = false, countryName: String, countryCode: String): DataResponse<Country>

    suspend fun getCountryCodeFromCache(): Result<Map<String?, String?>>

    suspend fun getLanguages(): Result<Map<String, String>>

    suspend fun updateFavCountry(country: Country, isFavorite: Boolean)

    suspend fun getLatLngFavCountries(): List<LatLng>

}