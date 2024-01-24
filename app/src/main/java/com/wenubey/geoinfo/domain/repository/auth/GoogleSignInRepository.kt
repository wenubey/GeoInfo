package com.wenubey.geoinfo.domain.repository.auth


import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.wenubey.geoinfo.utils.Resource

interface GoogleSignInRepository {

    val isUserAuthenticatedInFirebase: Boolean

    suspend fun oneTapSignInWithGoogle(): Resource<BeginSignInResult>

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Resource<Boolean>
}