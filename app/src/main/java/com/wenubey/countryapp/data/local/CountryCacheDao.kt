package com.wenubey.countryapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.wenubey.countryapp.data.local.entities.CountryCacheEntity

@Dao
interface CountryCacheDao {
    @Upsert
    fun upsertAll(countries: List<CountryCacheEntity>)

    @Query("SELECT * FROM countriesCache")
    fun getAllCountriesFromCache(): List<CountryCacheEntity>

    @Query("SELECT * FROM countriesCache WHERE countryCommonName LIKE '%' || :query || '%'")
    fun getCountry(query: String): CountryCacheEntity

    @Query("DELETE FROM countriesCache")
    suspend fun clearALl()

}