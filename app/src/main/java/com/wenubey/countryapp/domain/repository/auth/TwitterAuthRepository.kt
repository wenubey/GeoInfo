package com.wenubey.countryapp.domain.repository.auth

import android.app.Activity
import com.google.firebase.auth.FirebaseUser
import com.wenubey.countryapp.utils.Resource

interface TwitterAuthRepository {

    val currentUser: FirebaseUser?

    suspend fun signUpWithTwitter(activity: Activity): Resource<Boolean>
}