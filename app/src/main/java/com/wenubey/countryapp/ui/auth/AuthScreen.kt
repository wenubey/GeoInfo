package com.wenubey.countryapp.ui.auth

import androidx.compose.runtime.Composable


//TODO: create auth screen
@Composable
fun AuthScreen(
    authViewModel: AuthViewModel
) {

    authViewModel.authState.let { state ->
        when( state) {
            is AuthenticationState.Error -> {

            }
            is AuthenticationState.Success -> {

            }
        }
    }
}