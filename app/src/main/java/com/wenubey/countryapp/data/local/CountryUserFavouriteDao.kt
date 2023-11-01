package com.wenubey.countryapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.wenubey.countryapp.data.local.entities.CountryFavEntity

@Dao
interface CountryUserFavouriteDao {

    @Upsert
    fun upsertFavCountry(country: CountryFavEntity)

    @Query("SELECT * FROM countriesFav")
    fun getAllFavCountries(): List<CountryFavEntity>

    @Query("DELETE FROM countriesFav")
    fun clearAllFavourites()

    @Delete
    fun deleteFavCountry(countryFavEntity: CountryFavEntity)
}