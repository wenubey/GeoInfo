package com.wenubey.countryapp.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.wenubey.countryapp.ui.navigation.Screen
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.Constants.TAG
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun AuthState(
    navController: NavHostController,
    authViewModel: AuthViewModel = koinViewModel(),
) {
    val isUserSignedOut = authViewModel.getAuthState().collectAsStateWithLifecycle().value
    Log.i(TAG, "isUserSignedOut: $isUserSignedOut")
    if (isUserSignedOut) {
        NavigateToSignInScreen(navController = navController)
    } else {
        if (authViewModel.isEmailVerified) {
            NavigateToMapsScreen(navController = navController)
        } else {
            NavigateToVerifyEmailScreen(navController = navController)
        }
    }
}

@Composable
private fun NavigateToSignInScreen(navController: NavController) =
    navController.navigate(Screen.SignInScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

@Composable
private fun NavigateToMapsScreen(navController: NavController) =
    navController.navigate(Screen.TabLayoutScreen.route + "/${Locale.getDefault().displayCountry}/${Constants.MAP_SCREEN}") {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

@Composable
private fun NavigateToVerifyEmailScreen(navController: NavController) = navController.navigate(
    Screen.VerifyEmailScreen.route
) {
    popUpTo(navController.graph.id) {
        inclusive = true
    }
}