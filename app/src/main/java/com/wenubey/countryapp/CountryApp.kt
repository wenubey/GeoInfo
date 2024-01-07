package com.wenubey.countryapp

import android.app.Application
import com.wenubey.countryapp.di.firebaseModules
import com.wenubey.countryapp.di.databaseModule
import com.wenubey.countryapp.di.deepLinkModule
import com.wenubey.countryapp.di.repositoryModule
import com.wenubey.countryapp.di.retrofitModules
import com.wenubey.countryapp.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CountryApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CountryApp)
            modules(
                firebaseModules,
                databaseModule,
                repositoryModule,
                viewModelModules,
                retrofitModules,
                deepLinkModule,
            )
        }
    }
}