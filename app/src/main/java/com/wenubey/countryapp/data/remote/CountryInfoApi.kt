package com.wenubey.countryapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CountryInfoApi {

    @GET("name/all")
    suspend fun getAllCountries(): List<CountryDto>
}

interface CountryHistoryApi {

    @GET("historicalevents")
    suspend fun getHistoricalEvents(@Query("text") countryName: String): List<HistoryDto>
}