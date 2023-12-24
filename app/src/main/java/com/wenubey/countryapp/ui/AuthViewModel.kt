package com.wenubey.countryapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenubey.countryapp.domain.repository.auth.EmailAuthRepository

class AuthViewModel(
    private val repo: EmailAuthRepository
): ViewModel() {

    init {
        getAuthState()
    }
    fun getAuthState() = repo.getAuthState(viewModelScope)

    val isEmailVerified get() = repo.currentUser?.isEmailVerified ?: false
}