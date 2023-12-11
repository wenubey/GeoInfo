package com.wenubey.countryapp.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.wenubey.countryapp.ui.country.detail.CountryDetailScreen
import com.wenubey.countryapp.ui.email_verify.VerifyEmailScreen
import com.wenubey.countryapp.ui.forgot_password.ForgotPasswordScreen
import com.wenubey.countryapp.ui.map.MapScreen
import com.wenubey.countryapp.ui.profile.ProfileScreen
import com.wenubey.countryapp.ui.sign_in.SignInScreen
import com.wenubey.countryapp.ui.sign_up.SignUpScreen
import java.util.Locale

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
                navigateToMapScreen = { navHostController.navigate(Screen.MapScreen.route + "/${Locale.getDefault().displayCountry}") },
            )
        }
        composable(route = Screen.MapScreen.route + "/{countryName}",
            arguments = listOf(
                navArgument("countryName") {
                    type = NavType.StringType
                    defaultValue = Locale.getDefault().displayCountry
                }
            )
        ) {
            MapScreen(
                navigateToProfileScreen = {
                    navHostController.navigate(Screen.ProfileScreen.route)
                },
                navigateToCountryDetailScreen = { countryCode, countryName ->
                    if (countryCode != null && countryName != null) {
                        navHostController.navigate(Screen.CountryDetailScreen.route + "/${countryCode}/${countryName}")
                    }
                },
                countryName = it.arguments?.getString("countryName") ?: Locale.getDefault().displayName
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
        composable(
            route = Screen.CountryDetailScreen.route + "/{countryCode}/{countryName}",
            arguments = listOf(
                navArgument("countryCode") {
                    type = NavType.StringType
                },
                navArgument("countryName") {
                    type = NavType.StringType
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://countryapp.com/detail/{countryCode}/{countryName}"
                    action = Intent.ACTION_VIEW
                }
            )
        ) {

            val code = it.arguments?.getString("countryCode")
            val name = it.arguments?.getString("countryName")
            CountryDetailScreen(
                countryCode = code ?: "",
                countryName = name ?: "",
                navigateBack = { navHostController.popBackStack() },
                navigateToMapScreen = { countryName ->
                    navHostController.navigate(Screen.MapScreen.route + "/${countryName}")
                }
            )
        }
    }


}


