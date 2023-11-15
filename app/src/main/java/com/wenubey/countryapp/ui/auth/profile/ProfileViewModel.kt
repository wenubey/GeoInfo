package com.wenubey.countryapp.ui.auth.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenubey.countryapp.domain.model.User
import com.wenubey.countryapp.domain.repository.auth.ProfileRepository
import com.wenubey.countryapp.utils.Resource
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repo: ProfileRepository
) :ViewModel() {
    var revokeAccessResponse by mutableStateOf<Resource<Boolean>>(Resource.Success(false))

    var reloadUserResponse by mutableStateOf<Resource<Boolean>>(Resource.Success(false))

    var updateUserResponse by mutableStateOf<Resource<Boolean>>(Resource.Success(false))

    var currentUserDataResponse by mutableStateOf<User?>(null)

    val currentUser get() = repo.currentUser

    init {
        getUserData()
    }
    private fun getUserData() = viewModelScope.launch {
        currentUserDataResponse = repo.currentUserData()

    }
    fun reloadUser() = viewModelScope.launch {
        reloadUserResponse = Resource.Loading

        reloadUserResponse = repo.reloadUser()
    }

    val isEmailVerified get() = repo.currentUser?.isEmailVerified ?: false

    fun signOut() = repo.signOut()

    fun revokeAccess() = viewModelScope.launch {
        revokeAccessResponse = Resource.Loading

        revokeAccessResponse = repo.revokeAccess()
    }

    fun updateUser(newDisplayName: String, email: String) = viewModelScope.launch {
        updateUserResponse = Resource.Loading

        updateUserResponse = repo.updateUser(newDisplayName, email)
    }
}