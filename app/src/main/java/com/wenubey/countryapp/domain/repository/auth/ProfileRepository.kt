package com.wenubey.countryapp.domain.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.wenubey.countryapp.utils.Resource

interface ProfileRepository {

    val currentUser: FirebaseUser?

    fun signOut(): Resource<Boolean>

    suspend fun revokeAccess(): Resource<Boolean>

    suspend fun reloadUser(): Resource<Boolean>

    suspend fun updateUser(newDisplayName: String, email: String) : Resource<Boolean>
}