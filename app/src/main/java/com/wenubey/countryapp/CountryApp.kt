package com.wenubey.countryapp

import android.app.Application
import com.wenubey.countryapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CountryApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CountryApp)
            modules(appModules)
        }
    }
}