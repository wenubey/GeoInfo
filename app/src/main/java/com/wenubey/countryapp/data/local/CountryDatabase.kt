package com.wenubey.countryapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wenubey.countryapp.data.local.entities.CountryEntity

@Database(
    entities = [CountryEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class CountryDatabase() : RoomDatabase() {
    abstract val countryCacheDo: CountryCacheDao
}