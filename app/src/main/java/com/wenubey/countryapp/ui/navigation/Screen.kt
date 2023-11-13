package com.wenubey.countryapp.ui.navigation

sealed class Screen(val route: String) {
    object SignUpScreen: Screen(route = "signUpScreen")

    object SignInScreen: Screen(route = "signInScreen")

    object MapScreen: Screen(route = "mapScreen")

    object ProfileScreen: Screen(route = "profileScreen")

    object VerifyEmailScreen: Screen(route =  "verifyEmailScreen")

    object ListScreen: Screen(route = "listScreen")

    data class DetailScreen(val countryName: String): Screen(route = "detailScreen")

}