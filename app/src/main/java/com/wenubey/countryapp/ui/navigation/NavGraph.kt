package com.wenubey.countryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wenubey.countryapp.ui.auth.AuthScreen
import com.wenubey.countryapp.ui.country.CountryViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
    countryViewModel: CountryViewModel,
    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AuthScreen.route,
    ) {
        composable(route = Screen.AuthScreen.route) {
            AuthScreen()
        }
        composable(route = Screen.MapScreen.route) {
            //MapScreen()
        }
    }
}