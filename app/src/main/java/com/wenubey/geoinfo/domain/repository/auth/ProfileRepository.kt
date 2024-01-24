package com.wenubey.geoinfo.domain.repository.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.wenubey.geoinfo.domain.model.User
import com.wenubey.geoinfo.utils.Resource

interface ProfileRepository {

    val currentUser: FirebaseUser?

    suspend fun currentUserData(): User?

    fun signOut(): Resource<Boolean>

    suspend fun revokeAccess(): Resource<Boolean>

    suspend fun reloadUser(): Resource<Boolean>

    suspend fun updateUser(newDisplayName: String, email: String, phoneNumber: String) : Resource<Boolean>

    suspend fun updateProfilePhoto(newPhotoUri: Uri?) : Resource<Boolean>


}