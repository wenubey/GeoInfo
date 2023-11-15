package com.wenubey.countryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wenubey.countryapp.ui.auth.email_verify.VerifyEmailScreen
import com.wenubey.countryapp.ui.auth.profile.ProfileScreen
import com.wenubey.countryapp.ui.auth.sign_in.SignInScreen
import com.wenubey.countryapp.ui.auth.sign_up.SignUpScreen
import com.wenubey.countryapp.ui.map.MapScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.SignInScreen.route,
    ) {
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(
                navigateBack = {
                    navHostController.popBackStack()
                },
            )
        }
        composable(route = Screen.SignInScreen.route) {
            SignInScreen(
                navigateToForgotPasswordScreen = { /*TODO*/ },
                navigateToSignUpScreen = { navHostController.navigate(Screen.SignUpScreen.route) },
                navigateToProfileScreen = {navHostController.navigate(Screen.MapScreen.route)},
            )
        }
        composable(route = Screen.MapScreen.route) {
            MapScreen(
                navigateToProfileScreen = {
                    navHostController.navigate(Screen.ProfileScreen.route)
                }
            )
        }
        composable(route = Screen.VerifyEmailScreen.route) {
            VerifyEmailScreen(
                navigateToProfileScreen = {
                    navHostController.navigate(Screen.ProfileScreen.route)
                }
            )
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}