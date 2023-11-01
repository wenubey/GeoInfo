package com.wenubey.countryapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.wenubey.countryapp.data.local.entities.CountryEntity

@Dao
interface CountryCacheDao {
    @Upsert
    fun upsertAll(countries: List<CountryEntity>)

    @Query("SELECT * FROM countriesCache")
    fun getAllCountriesFromCache(): List<CountryEntity>

    @Query("SELECT * FROM countriesCache WHERE countryCommonName LIKE '%' || :query || '%'")
    fun getSearchedCountries(query: String): List<CountryEntity>

    @Query("DELETE FROM countriesCache")
    suspend fun clearALl()

}