package com.wenubey.countryapp.ui.navigation

sealed class Screen(val route: String) {
    object AuthScreen : Screen(route = "authScreen")

    object MapScreen: Screen(route = "mapScreen")

    object ListScreen: Screen(route = "listScreen")

    data class DetailScreen(val countryName: String): Screen(route = "detailScreen")

}