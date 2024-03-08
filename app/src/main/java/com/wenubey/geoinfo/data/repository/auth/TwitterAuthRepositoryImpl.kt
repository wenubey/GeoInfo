package com.wenubey.geoinfo.data.repository.auth

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.wenubey.geoinfo.domain.repository.auth.TwitterAuthRepository
import com.wenubey.geoinfo.utils.AuthProvider
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.addUserToFirestore
import com.wenubey.geoinfo.utils.getCurrentTime
import kotlinx.coroutines.tasks.await



class TwitterAuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
):TwitterAuthRepository  {


        override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun signUpWithTwitter(activity: Activity): Resource<Boolean> {
        return try {
            val provider = OAuthProvider.newBuilder("twitter.com").build()
            val result = auth.startActivityForSignInWithProvider(activity, provider).await()
            val isUserNew = result.additionalUserInfo?.isNewUser ?: false
            Log.i(TAG, "signUpWithTwitter isUserNew: $isUserNew")
            Log.i(TAG, "signUpWithTwitter: currentUserUID: ${auth.currentUser?.uid}")
            if (isUserNew) {
                addUserToFirestore(auth = auth, db = db, AuthProvider.TWITTER, createdAt = getCurrentTime())
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    companion object {
        private const val TAG = "TwitterAuthRepositoryImpl"
    }
}