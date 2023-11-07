package com.wenubey.countryapp.di

import androidx.room.Room
import com.wenubey.countryapp.data.local.CountryDatabase
import com.wenubey.countryapp.data.remote.CountryHistoryApi
import com.wenubey.countryapp.data.remote.CountryInfoApi
import com.wenubey.countryapp.domain.repository.CountryRepository
import com.wenubey.countryapp.data.repository.CountryRepositoryImpl
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.utils.Constants.BASE_URL_COUNTRIES
import com.wenubey.countryapp.utils.Constants.BASE_URL_HISTORIES
import com.wenubey.countryapp.utils.Constants.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            databaseModule,
            repositoryModule,
            viewModelModule,
            retrofitModules
        )
    )
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            CountryDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
    factory { get<CountryDatabase>().countryCacheDao }
    factory { get<CountryDatabase>().countryUserFavouriteDao }
}

val repositoryModule = module {
    single<CountryRepository> { CountryRepositoryImpl(get(), get(), get(), get()) }
}


val viewModelModule = module {
    viewModel { CountryViewModel(get()) }
}

val retrofitModules = module {
    single<CountryInfoApi> {
         Retrofit.Builder()
            .baseUrl(BASE_URL_COUNTRIES)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
    single<CountryHistoryApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL_HISTORIES)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}


