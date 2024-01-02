package com.wenubey.countryapp.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.wenubey.countryapp.domain.repository.auth.EmailAuthRepository
import com.wenubey.countryapp.utils.AuthProvider
import com.wenubey.countryapp.utils.Resource
import com.wenubey.countryapp.utils.addUserToFirestore
import com.wenubey.countryapp.utils.getCurrentTime
import kotlinx.coroutines.tasks.await


class EmailAuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,

    ) : EmailAuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<Boolean> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val isNewUser = result.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                addUserToFirestore(auth, db, AuthProvider.EMAIL, createdAt = getCurrentTime())
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun sendEmailVerification(): Resource<Boolean> {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Resource<Boolean> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override fun getAuthState(): Boolean =  auth.currentUser == null

}
