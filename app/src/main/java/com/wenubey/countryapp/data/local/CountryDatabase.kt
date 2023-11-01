package com.wenubey.countryapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wenubey.countryapp.data.local.entities.CountryEntity
import com.wenubey.countryapp.data.local.entities.CountryFavEntity

@Database(
    entities = [CountryEntity::class, CountryFavEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(CountryAppTypeConverter::class)
abstract class CountryDatabase : RoomDatabase() {
    abstract val countryCacheDo: CountryCacheDao
    abstract val countryUserFavouriteDao: CountryUserFavouriteDao
}