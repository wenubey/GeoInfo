package com.wenubey.countryapp.di


import android.content.Context
import android.location.Geocoder
import androidx.room.Room
import com.wenubey.countryapp.data.local.CountryDatabase
import com.wenubey.countryapp.data.remote.CountryHistoryApi
import com.wenubey.countryapp.data.remote.CountryInfoApi
import com.wenubey.countryapp.data.repository.CountryRepositoryImpl
import com.wenubey.countryapp.data.repository.auth.EmailAuthRepositoryImpl
import com.wenubey.countryapp.data.repository.auth.FacebookAuthRepositoryImpl
import com.wenubey.countryapp.data.repository.auth.GoogleSignInRepositoryImpl
import com.wenubey.countryapp.data.repository.auth.ProfileRepositoryImpl
import com.wenubey.countryapp.data.repository.auth.TwitterAuthRepositoryImpl
import com.wenubey.countryapp.domain.repository.CountryRepository
import com.wenubey.countryapp.domain.repository.auth.EmailAuthRepository
import com.wenubey.countryapp.domain.repository.auth.FacebookAuthRepository
import com.wenubey.countryapp.domain.repository.auth.GoogleSignInRepository
import com.wenubey.countryapp.domain.repository.auth.ProfileRepository
import com.wenubey.countryapp.domain.repository.auth.TwitterAuthRepository
import com.wenubey.countryapp.ui.AuthViewModel
import com.wenubey.countryapp.ui.profile.ProfileViewModel
import com.wenubey.countryapp.ui.sign_in.SignInViewModel
import com.wenubey.countryapp.ui.sign_up.SignUpViewModel
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.forgot_password.ForgotPasswordViewModel
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.Constants.BASE_URL_COUNTRIES
import com.wenubey.countryapp.utils.Constants.BASE_URL_HISTORIES
import com.wenubey.countryapp.utils.Constants.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
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
            viewModelModules,
            retrofitModules,
            authModule,
            mapModules
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
    factory<EmailAuthRepository> { EmailAuthRepositoryImpl(get(), get()) }
    factory<FacebookAuthRepository> { FacebookAuthRepositoryImpl(get(), get()) }
    factory<GoogleSignInRepository> {
        GoogleSignInRepositoryImpl(
            get(),
            get(),
            get(),
            get(named(Constants.SIGN_IN_REQUEST)),
            get(named(Constants.SIGN_UP_REQUEST))
        )
    }
    factory<TwitterAuthRepository> { TwitterAuthRepositoryImpl(get(), get()) }
    factory<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
}

val viewModelModules = module {
    viewModel { CountryViewModel(get()) }
    viewModel { SignInViewModel(get(), get(), get(), get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { ForgotPasswordViewModel(get(), get()) }
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

val mapModules = module {
    single { provideGeocoder(get()) }
}

fun provideGeocoder(context: Context): Geocoder {
    return Geocoder(context)
}


