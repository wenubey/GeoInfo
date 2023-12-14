package com.wenubey.countryapp.ui.navigation

import com.wenubey.countryapp.utils.Constants

sealed class Screen(val route: String) {
    object SignUpScreen: Screen(route = "signUpScreen")

    object SignInScreen: Screen(route = "signInScreen")

    data class TabLayoutScreen(val args: String?, val subRoute: String = Constants.MAP_SCREEN): Screen(route = "tabLayoutScreen/${args}/$subRoute")

    object ForgotPasswordScreen: Screen(route = "forgotPassword")

    object VerifyEmailScreen: Screen(route =  "verifyEmailScreen")

    object CountryDetailScreen: Screen(route = "countryDetailScreen")

}