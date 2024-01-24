package com.wenubey.geoinfo.data.repository.auth

import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wenubey.geoinfo.domain.repository.auth.GoogleSignInRepository
import com.wenubey.geoinfo.utils.AuthProvider
import com.wenubey.geoinfo.utils.Constants.SIGN_IN_REQUEST
import com.wenubey.geoinfo.utils.Constants.SIGN_UP_REQUEST
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.addUserToFirestore
import com.wenubey.geoinfo.utils.getCurrentTime
import kotlinx.coroutines.tasks.await
import javax.inject.Named

class GoogleSignInRepositoryImpl(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    private val db: FirebaseFirestore,
    @Named(SIGN_IN_REQUEST) private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST) private var signUpRequest: BeginSignInRequest,
) : GoogleSignInRepository {

    override val isUserAuthenticatedInFirebase: Boolean
        get() = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle(): Resource<BeginSignInResult> {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener { Log.w(TAG, "oneTapSignInWithGoogle:Success", ) }
                .addOnFailureListener { Log.e(TAG, "oneTapSignInWithGoogle:Error", it) }.await()
            Resource.Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Resource.Success(signUpResult)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
    }

    override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Resource<Boolean> {
        return try {
            val authResult = auth.signInWithCredential(googleCredential)
                .addOnSuccessListener { Log.w(TAG, "oneTapSignInWithGoogle:Success", ) }
                .addOnFailureListener { Log.e(TAG, "oneTapSignInWithGoogle:Error", it) }.await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                addUserToFirestore(db = db, auth = auth, authProvider = AuthProvider.GOOGLE, createdAt = getCurrentTime())
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    companion object {
        private const val TAG = "googleSignInRepositoryImpl"
    }
}