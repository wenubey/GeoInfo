package com.wenubey.geoinfo

import android.app.Application
import com.wenubey.geoinfo.di.firebaseModules
import com.wenubey.geoinfo.di.databaseModule
import com.wenubey.geoinfo.di.deepLinkModule
import com.wenubey.geoinfo.di.repositoryModule
import com.wenubey.geoinfo.di.retrofitModules
import com.wenubey.geoinfo.di.viewModelModules
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