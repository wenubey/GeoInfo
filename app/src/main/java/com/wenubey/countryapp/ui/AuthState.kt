package com.wenubey.countryapp.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.wenubey.countryapp.ui.navigation.Screen
import com.wenubey.countryapp.utils.Constants.TAG
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun AuthState(
    navHostController: NavHostController,
    viewModel: AuthViewModel = koinViewModel(),
) {
    val isUserSignedOut = viewModel.getAuthState().collectAsState().value
    if (isUserSignedOut) {
        Log.i(TAG, "viewModel.isUserSignedOut: $isUserSignedOut ")
        NavigateToSignInScreen(navController = navHostController)
    } else {
        if (viewModel.isEmailVerified) {
            Log.i(TAG, "viewModel.isEmailVerified: ${viewModel.isEmailVerified}")
            NavigateToMapsScreen(navController = navHostController)
        } else {
            Log.i(TAG, "viewModel.isEmailNotVerified: ${viewModel.isEmailVerified}")
            NavigateToVerifyEmailScreen(navController = navHostController)
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
    navController.navigate(Screen.TabLayoutScreen(args = "/${Locale.getDefault().displayCountry}").route) {
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