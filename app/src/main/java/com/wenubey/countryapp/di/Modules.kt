package com.wenubey.countryapp.di

import androidx.room.Room
import com.wenubey.countryapp.data.local.CountryCacheDao
import com.wenubey.countryapp.data.local.CountryDatabase
import com.wenubey.countryapp.utils.Constants.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModules = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            CountryDatabase::class.java,
            DATABASE_NAME
        )
    }
    single {
        val database = get<CountryDatabase>()
        database.countryCacheDo
    }
}

