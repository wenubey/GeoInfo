package com.wenubey.countryapp.domain.repository.auth

import android.app.Activity
import com.google.firebase.auth.FirebaseUser
import com.wenubey.countryapp.utils.Resource

interface FacebookAuthRepository {

    val currentUser: FirebaseUser?
    suspend fun signUpWithFacebook(activity: Activity): Resource<Boolean>
}