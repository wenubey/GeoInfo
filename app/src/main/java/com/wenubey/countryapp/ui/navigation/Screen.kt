package com.wenubey.countryapp.ui.navigation

sealed class Screen(val route: String) {
    object SignUpScreen: Screen(route = "signUpScreen")

    object SignInScreen: Screen(route = "signInScreen")

    data class TabLayoutScreen(val args: String?): Screen(route = "tabLayoutScreen/${args}")

    object MapScreen: Screen(route = "mapScreen")

    object ProfileScreen: Screen(route = "profileScreen")

    object ForgotPasswordScreen: Screen(route = "forgotPassword")

    object VerifyEmailScreen: Screen(route =  "verifyEmailScreen")

    object CountryDetailScreen: Screen(route = "countryDetailScreen")

}