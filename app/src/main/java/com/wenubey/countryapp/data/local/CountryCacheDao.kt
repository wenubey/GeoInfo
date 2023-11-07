package com.wenubey.countryapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.wenubey.countryapp.data.local.entities.CountryCacheEntity

@Dao
interface CountryCacheDao {
    @Upsert
    suspend fun upsertAll(countries: List<CountryCacheEntity>)

    @Upsert
    suspend fun upsertCountry(country: CountryCacheEntity)

    @Query("SELECT * FROM countriesCache")
    suspend fun getAllCountriesFromCache(): List<CountryCacheEntity>

    @Query("SELECT * FROM countriesCache WHERE countryCommonName LIKE '%' || :query || '%'")
    suspend fun getCountry(query: String): CountryCacheEntity?

    @Query("DELETE FROM countriesCache")
    suspend fun clearALl()

    @Query("DELETE FROM countriesCache WHERE countryCommonName LIKE '%' || :name || '%'")
    suspend fun clearCountry(name: String)

}