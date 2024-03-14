package com.wenubey.geoinfo.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.wenubey.geoinfo.ui.country.detail.CountryDetailScreen
import com.wenubey.geoinfo.ui.email_verify.VerifyEmailScreen
import com.wenubey.geoinfo.ui.forgot_password.ForgotPasswordScreen
import com.wenubey.geoinfo.ui.sign_in.SignInScreen
import com.wenubey.geoinfo.ui.sign_up.SignUpScreen
import com.wenubey.geoinfo.ui.tab_screen.TabLayoutScreen
import com.wenubey.geoinfo.ui.tab_screen.TabViewModel
import java.util.Locale

@Composable
fun NavGraph(
    navController: NavHostController,
    tabViewModel: TabViewModel,
    startDestination: String
) {
    val context = LocalContext.current
    val localeCountry = context.resources.configuration.locales[0].displayCountry

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {

        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(
                navigateToSignInScreen = {
                    navController.popBackStack()
                },
            )
        }
        composable(route = Screen.SignInScreen.route) {
            SignInScreen(
                navigateToForgotPasswordScreen = { navController.navigate(Screen.ForgotPasswordScreen.route) },
                navigateToSignUpScreen = { navController.navigate(Screen.SignUpScreen.route) },
                navigateToMapScreen = {
                    navController.navigate(
                        Screen.TabLayoutScreen.route + "/$localeCountry"
                    )
                },
            )
        }
        composable(route = Screen.TabLayoutScreen.route + "/{countryName}",
            arguments = listOf(
                navArgument("countryName") {
                    type = NavType.StringType
                    defaultValue = Locale.getDefault().displayCountry
                },
            ),
        ) {
            TabLayoutScreen(
                navigateToCountryDetailScreen = { countryCode, countryName ->
                    if (countryCode != null && countryName != null) {
                        navController.navigate(Screen.CountryDetailScreen.route + "/${countryCode}/${countryName}")
                    }
                },
                navigateToForgotPasswordScreen = { email ->
                    navController.navigate(Screen.ForgotPasswordScreen.route + "/${email}")
                },
                countryName = it.arguments?.getString("countryName")
                    ?: localeCountry,
                tabViewModel = tabViewModel,
                navigateToSignInScreen = {
                    navController.navigate(Screen.SignInScreen.route)
                }
            )
        }
        composable(route = Screen.VerifyEmailScreen.route) {
            VerifyEmailScreen(
                navigateToMapScreen = {
                    navController.navigate(
                        Screen.TabLayoutScreen.route + "/$localeCountry"
                    ) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.ForgotPasswordScreen.route,
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
        ) {
            ForgotPasswordScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                email = it.arguments?.getString("email")
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
                navController.popBackStack()
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
                    uriPattern = "https://GeoInfo.com/detail/{countryCode}/{countryName}"
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            val code = it.arguments?.getString("countryCode")
            val name = it.arguments?.getString("countryName")
            CountryDetailScreen(
                countryCode = code ?: "",
                countryName = name ?: "",
                navigateBack = { navController.popBackStack() },
                navigateToMapScreen = { countryName ->
                    navController.navigate(
                        Screen.TabLayoutScreen.route + "/${countryName}"
                    )
                }
            )
        }
    }
}


