package com.wenubey.countryapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wenubey.countryapp.domain.repository.auth.EmailAuthRepository
import com.wenubey.countryapp.ui.navigation.Screen
import java.util.Locale

class AuthViewModel(
    private val repo: EmailAuthRepository
): ViewModel() {
    private var authState by mutableStateOf(false)
    init {
        authState = repo.getAuthState()
    }
    fun getStartDestination(): String {
        return if (authState) {
            Screen.SignInScreen.route
        } else{
            if (isEmailVerified) {
                Screen.TabLayoutScreen.route +"/${Locale.getDefault().displayCountry}"
            }else {
                Screen.VerifyEmailScreen.route
            }
        }
    }

    private val isEmailVerified get() = repo.currentUser?.isEmailVerified ?: false

}