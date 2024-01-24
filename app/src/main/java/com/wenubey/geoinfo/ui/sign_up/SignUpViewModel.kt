package com.wenubey.geoinfo.ui.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenubey.geoinfo.domain.repository.auth.EmailAuthRepository
import com.wenubey.geoinfo.utils.Resource
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repo: EmailAuthRepository
): ViewModel() {

    var signUpResponse by mutableStateOf<Resource<Boolean>>(Resource.Success(false))
        private set

    var sendEmailVerificationResponse by mutableStateOf<Resource<Boolean>>(Resource.Success(false))
        private set

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signUpResponse = Resource.Loading

        signUpResponse = repo.signUpWithEmailAndPassword(email, password)
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Resource.Loading

        sendEmailVerificationResponse = repo.sendEmailVerification()
    }
}