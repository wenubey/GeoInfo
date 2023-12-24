package com.wenubey.countryapp.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.wenubey.countryapp.ui.country.detail.CountryDetailScreen
import com.wenubey.countryapp.ui.email_verify.VerifyEmailScreen
import com.wenubey.countryapp.ui.forgot_password.ForgotPasswordScreen
import com.wenubey.countryapp.ui.sign_in.SignInScreen
import com.wenubey.countryapp.ui.sign_up.SignUpScreen
import com.wenubey.countryapp.ui.tab_screen.TabLayoutScreen
import com.wenubey.countryapp.ui.tab_screen.TabViewModel
import java.util.Locale

@Composable
fun NavGraph(
    navHostController: NavHostController,
    tabViewModel: TabViewModel,
) {
    val context = LocalContext.current
    val localeCountry = context.resources.configuration.locales[0].displayCountry
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
                navigateToMapScreen = {
                    navHostController.navigate(
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
                        navHostController.navigate(Screen.CountryDetailScreen.route + "/${countryCode}/${countryName}")
                    }
                },
                navigateToForgotPasswordScreen = { email ->
                    navHostController.navigate(Screen.ForgotPasswordScreen.route + "/${email}")
                },
                countryName = it.arguments?.getString("countryName")
                    ?: localeCountry,
                tabViewModel = tabViewModel
            )
        }
        composable(route = Screen.VerifyEmailScreen.route) {
            VerifyEmailScreen(
                navigateToMapScreen = {
                    navHostController.navigate(
                        Screen.TabLayoutScreen.route + "/$localeCountry"
                    ) {
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
                    navHostController.navigate(
                        Screen.TabLayoutScreen.route + "/${countryName}"
                    )
                }
            )
        }
    }


}


