package com.wenubey.countryapp.data.repository.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.wenubey.countryapp.domain.model.User
import com.wenubey.countryapp.domain.repository.auth.ProfileRepository
import com.wenubey.countryapp.utils.Constants.TAG
import com.wenubey.countryapp.utils.Constants.USERS
import com.wenubey.countryapp.utils.Resource
import kotlinx.coroutines.tasks.await

class ProfileRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ProfileRepository {
    override val currentUser: FirebaseUser?
        get() = auth.currentUser


    override fun signOut(): Resource<Boolean> {
        return try {
            auth.signOut()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun currentUserData(): User? {
        return try {
            val document = db.collection(USERS).document(currentUser!!.uid).get().await()
            if (document.exists()) {
                document.toObject(User::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "getUserData error: $e")
            null
        }
    }

    override suspend fun revokeAccess(): Resource<Boolean> {
        return try {
            db.collection(USERS).document(auth.currentUser?.uid!!).delete()
            auth.currentUser?.delete()?.await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun reloadUser(): Resource<Boolean> {
        return try {
            auth.currentUser?.reload()?.await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun updateUser(newDisplayName: String, email: String): Resource<Boolean> {
        return try {
            val profileUpdates = userProfileChangeRequest {
                displayName = newDisplayName
            }
            auth.currentUser?.updateProfile(profileUpdates)?.await()
            auth.currentUser?.updateEmail(email)?.await()
            val updatedValue = mapOf(
                "displayName" to newDisplayName,
                "email" to email
            )
            db.collection(USERS).document(auth.currentUser!!.uid).update(updatedValue)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


}