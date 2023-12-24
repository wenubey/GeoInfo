package com.wenubey.countryapp.ui.navigation


sealed class Screen(val route: String) {
    object SignUpScreen: Screen(route = "signUpScreen")

    object SignInScreen: Screen(route = "signInScreen")

    object TabLayoutScreen: Screen(route = "tabLayoutScreen")

    object ForgotPasswordScreen: Screen(route = "forgotPassword")

    object VerifyEmailScreen: Screen(route =  "verifyEmailScreen")

    object CountryDetailScreen: Screen(route = "countryDetailScreen")

}