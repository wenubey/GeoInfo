package com.wenubey.countryapp.ui.forgot_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenubey.countryapp.domain.repository.auth.EmailAuthRepository
import com.wenubey.countryapp.domain.repository.auth.ProfileRepository
import com.wenubey.countryapp.utils.AuthProvider
import com.wenubey.countryapp.utils.Resource
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val repo: EmailAuthRepository,
    private val profileRepo: ProfileRepository,
): ViewModel() {

    var isSentPasswordReset by mutableStateOf<Resource<Boolean>>(Resource.Success(false))

    var currentUserAuthProvider by mutableStateOf<AuthProvider?>(null)

    init {
        getUserData()
    }
    private fun getUserData() = viewModelScope.launch {
        val currentUserData = profileRepo.currentUserData()

        currentUserAuthProvider = currentUserData?.authProvider

    }

    fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        isSentPasswordReset = Resource.Loading

        isSentPasswordReset = repo.sendPasswordResetEmail(email)
    }
}