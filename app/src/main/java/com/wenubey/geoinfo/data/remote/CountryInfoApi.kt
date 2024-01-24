package com.wenubey.geoinfo.data.remote

import com.wenubey.geoinfo.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CountryInfoApi {

    @GET("all")
    suspend fun getAllCountries(): List<CountryDto>

    @GET("alpha/{countryCode}")
    suspend fun getCountry(@Path("countryCode") countryCode: String): List<CountryDto>
}

interface CountryHistoryApi {

    @Headers("x-api-key: ${BuildConfig.X_API_KEY}")
    @GET("historicalevents")
    suspend fun getHistoricalEvents(@Query("text") countryName: String): List<HistoryDto>
}