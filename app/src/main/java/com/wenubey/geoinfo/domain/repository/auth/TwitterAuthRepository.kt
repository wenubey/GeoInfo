package com.wenubey.geoinfo.domain.repository.auth

import android.app.Activity
import com.google.firebase.auth.FirebaseUser
import com.wenubey.geoinfo.utils.Resource

interface TwitterAuthRepository {

    val currentUser: FirebaseUser?

    suspend fun signUpWithTwitter(activity: Activity): Resource<Boolean>
}