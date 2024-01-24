package com.wenubey.geoinfo.domain.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.wenubey.geoinfo.utils.Resource

interface EmailAuthRepository {

    val currentUser: FirebaseUser?

    suspend fun signUpWithEmailAndPassword(email: String, password: String): Resource<Boolean>

    suspend fun sendEmailVerification(): Resource<Boolean>

    suspend fun signInWithEmailAndPassword(email: String, password: String): Resource<Boolean>

    suspend fun sendPasswordResetEmail(email: String): Resource<Boolean>

    fun getAuthState(): Boolean


}
