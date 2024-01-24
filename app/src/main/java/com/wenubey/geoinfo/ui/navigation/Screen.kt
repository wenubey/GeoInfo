package com.wenubey.geoinfo.ui.navigation


sealed class Screen(val route: String) {
    data object SignUpScreen: Screen(route = "signUpScreen")

    data object SignInScreen: Screen(route = "signInScreen")

    data object TabLayoutScreen: Screen(route = "tabLayoutScreen")

    data object ForgotPasswordScreen: Screen(route = "forgotPassword")

    data object VerifyEmailScreen: Screen(route =  "verifyEmailScreen")

    data object CountryDetailScreen: Screen(route = "countryDetailScreen")

}