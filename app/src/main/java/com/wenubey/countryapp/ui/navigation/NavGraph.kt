package com.wenubey.countryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wenubey.countryapp.ui.email_verify.VerifyEmailScreen
import com.wenubey.countryapp.ui.forgot_password.ForgotPasswordScreen
import com.wenubey.countryapp.ui.map.MapScreen
import com.wenubey.countryapp.ui.profile.ProfileScreen
import com.wenubey.countryapp.ui.sign_in.SignInScreen
import com.wenubey.countryapp.ui.sign_up.SignUpScreen

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
                navigateToForgotPasswordScreen = { navHostController.navigate(Screen.ForgotPasswordScreen.route) },
                navigateToSignUpScreen = { navHostController.navigate(Screen.SignUpScreen.route) },
                navigateToProfileScreen = { navHostController.navigate(Screen.MapScreen.route) },
            )
        }
        composable(route = Screen.MapScreen.route) {
            MapScreen(
                navigateToProfileScreen = {
                    navHostController.navigate(Screen.ProfileScreen.route)
                },
                navigateToCountryDetailScreen = { countryName ->
                    // TODO not implemented yet
                }
            )
        }
        composable(route = Screen.VerifyEmailScreen.route) {
            VerifyEmailScreen(
                navigateToProfileScreen = {
                    navHostController.navigate(Screen.ProfileScreen.route) {
                        popUpTo(navHostController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                navigateBack = {
                    navHostController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(
                navigateBack = {
                    navHostController.popBackStack()
                },
                navigateToForgotPasswordScreen = { email ->
                    navHostController.navigate(Screen.ForgotPasswordScreen.route + "/${email}")
                }
            )
        }
        composable(
            route = Screen.ForgotPasswordScreen.route + "/{email}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
        ) {
            ForgotPasswordScreen(
                navigateBack = {
                    navHostController.popBackStack()
                },
                email = it.arguments?.getString("email")
            )
        }
    }
}