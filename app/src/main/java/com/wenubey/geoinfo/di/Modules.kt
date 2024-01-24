package com.wenubey.geoinfo.di


import androidx.room.Room
import com.wenubey.geoinfo.data.local.CountryDatabase
import com.wenubey.geoinfo.data.remote.CountryHistoryApi
import com.wenubey.geoinfo.data.remote.CountryInfoApi
import com.wenubey.geoinfo.data.repository.CountryRepositoryImpl
import com.wenubey.geoinfo.data.repository.auth.EmailAuthRepositoryImpl
import com.wenubey.geoinfo.data.repository.auth.FacebookAuthRepositoryImpl
import com.wenubey.geoinfo.data.repository.auth.GoogleSignInRepositoryImpl
import com.wenubey.geoinfo.data.repository.auth.ProfileRepositoryImpl
import com.wenubey.geoinfo.data.repository.auth.TwitterAuthRepositoryImpl
import com.wenubey.geoinfo.domain.repository.CountryRepository
import com.wenubey.geoinfo.domain.repository.auth.EmailAuthRepository
import com.wenubey.geoinfo.domain.repository.auth.FacebookAuthRepository
import com.wenubey.geoinfo.domain.repository.auth.GoogleSignInRepository
import com.wenubey.geoinfo.domain.repository.auth.ProfileRepository
import com.wenubey.geoinfo.domain.repository.auth.TwitterAuthRepository
import com.wenubey.geoinfo.ui.AuthViewModel
import com.wenubey.geoinfo.ui.country.CountryViewModel
import com.wenubey.geoinfo.ui.deep_link.DeepLinkFacade
import com.wenubey.geoinfo.ui.deep_link.DeepLinkFacadeImpl
import com.wenubey.geoinfo.ui.deep_link.DeepLinkViewModel
import com.wenubey.geoinfo.ui.forgot_password.ForgotPasswordViewModel
import com.wenubey.geoinfo.ui.profile.ProfileViewModel
import com.wenubey.geoinfo.ui.sign_in.SignInViewModel
import com.wenubey.geoinfo.ui.sign_up.SignUpViewModel
import com.wenubey.geoinfo.ui.tab_screen.TabViewModel
import com.wenubey.geoinfo.utils.Constants
import com.wenubey.geoinfo.utils.Constants.BASE_URL_COUNTRIES
import com.wenubey.geoinfo.utils.Constants.BASE_URL_HISTORIES
import com.wenubey.geoinfo.utils.Constants.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


val deepLinkModule = module {
    factory<DeepLinkFacade> { DeepLinkFacadeImpl() }
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
}

val repositoryModule = module {
    single<CountryRepository> { CountryRepositoryImpl(get(), get(), get(), get(), get()) }
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
    factory<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get()) }
}

val viewModelModules = module {
    viewModel { CountryViewModel(get()) }
    viewModel { SignInViewModel(get(), get(), get(), get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { ForgotPasswordViewModel(get(), get()) }
    viewModel { DeepLinkViewModel(get()) }
    viewModel { TabViewModel(get()) }
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





