package com.wenubey.countryapp.ui.auth

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.wenubey.countryapp.ui.navigation.Screen
import com.wenubey.countryapp.utils.Constants.TAG
import org.koin.androidx.compose.getViewModel

@Composable
fun AuthState(
    navHostController: NavHostController
) {
    val viewModel: AuthViewModel = getViewModel()
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
    navController.navigate(Screen.MapScreen.route) {
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