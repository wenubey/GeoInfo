package com.wenubey.geoinfo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wenubey.geoinfo.data.local.entities.CountryCacheEntity

@Database(
    entities = [CountryCacheEntity::class],
    version = 1,
)
@TypeConverters(GeoInfoAppTypeConverter::class)
abstract class CountryDatabase : RoomDatabase() {
    abstract val countryCacheDao: CountryCacheDao
}