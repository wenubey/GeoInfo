package com.wenubey.countryapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.wenubey.countryapp.data.local.entities.CountryCacheEntity

@Dao
interface CountryCacheDao {
    @Upsert
    suspend fun upsertAll(countries: List<CountryCacheEntity>)

    @Upsert
    suspend fun upsertCountry(country: CountryCacheEntity)

    @Query("SELECT * FROM countriesCache ORDER BY countryCommonName")
    suspend fun getAllCountriesFromCache(): List<CountryCacheEntity>

    @Query("SELECT * FROM countriesCache WHERE countryCommonName LIKE '%' || :query || '%'")
    suspend fun getCountry(query: String): CountryCacheEntity?

    @Query("DELETE FROM countriesCache")
    suspend fun clearALl()

    @Query("DELETE FROM countriesCache WHERE countryCommonName LIKE '%' || :name || '%'")
    suspend fun clearCountry(name: String)

    @Query(
        """
        SELECT * FROM countriesCache WHERE
        (:query IS NULL OR countryCommonName LIKE '%' || :query || '%' OR region LIKE '%' || :query || '%') 
        ORDER BY 
            CASE
        WHEN :sortOption = 'NAME' AND :sortOrder = 'ASC' THEN countryCommonName END ASC,
    CASE
        WHEN :sortOption = 'NAME' AND :sortOrder = 'DESC' THEN countryCommonName END DESC,
    CASE
        WHEN :sortOption = 'AREA' AND :sortOrder = 'ASC' THEN area END ASC,
    CASE
        WHEN :sortOption = 'AREA' AND :sortOrder = 'DESC' THEN area END DESC,
    CASE
        WHEN :sortOption = 'POPULATION' AND :sortOrder = 'ASC' THEN population END ASC,
    CASE
        WHEN :sortOption = 'POPULATION' AND :sortOrder = 'DESC' THEN population END DESC,
    countryCommonName ASC;
    """
    )
    suspend fun getSortedFilteredCountries(query: String?, sortOption: String?, sortOrder: String?): List<CountryCacheEntity>


}