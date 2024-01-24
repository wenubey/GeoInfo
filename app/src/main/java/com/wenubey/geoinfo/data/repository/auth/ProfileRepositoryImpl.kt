package com.wenubey.geoinfo.data.repository.auth

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.wenubey.geoinfo.domain.model.User
import com.wenubey.geoinfo.domain.repository.auth.ProfileRepository
import com.wenubey.geoinfo.utils.Resource
import kotlinx.coroutines.tasks.await

class ProfileRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : ProfileRepository {
    override val currentUser: FirebaseUser?
        get() = auth.currentUser


    override fun signOut(): Resource<Boolean> {
        return try {
            auth.signOut()
            Log.w(TAG, "signOut:Success")
            Resource.Success(true)
        } catch (e: Exception) {
            Log.e(TAG, "signOut:Error: $e")
            Resource.Error(e)
        }
    }

    override suspend fun currentUserData(): User? {
        return try {
            val document =
                db.collection(USERS).document(currentUser!!.uid).get()
                    .addOnCompleteListener { Log.w(TAG, "currentUserData:Success") }
                    .addOnFailureListener {
                        Log.e(TAG, "currentUserData:Error: $it")
                        Resource.Error(it)
                    }
                    .await()
            if (document.exists()) {
                document.toObject(User::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "currentUserData:Error: $e")
            null
        }
    }

    override suspend fun revokeAccess(): Resource<Boolean> {
        return try {
            db.collection(USERS).document(auth.currentUser?.uid!!).delete()
            auth.currentUser?.delete()
                ?.addOnCompleteListener { Log.w(TAG, "revokeAccess:Success") }
                ?.addOnFailureListener { Log.e(TAG, "revokeAccess:Error: $it") }
                ?.await()
            Resource.Success(true)
        } catch (e: Exception) {
            Log.e(TAG, "revokeAccess:Error: $e")
            Resource.Error(e)
        }
    }

    override suspend fun reloadUser(): Resource<Boolean> {
        return try {
            auth.currentUser?.reload()
                ?.addOnFailureListener {
                    Log.e(TAG, "reloadUser:Error: $it")
                    Resource.Error(it)
                }
                ?.addOnCompleteListener { Log.w(TAG, "reloadUser:Success") }
                ?.await()
            Resource.Success(true)
        } catch (e: Exception) {
            Log.e(TAG, "reloadUser:Error: $e")
            Resource.Error(e)
        }
    }

    override suspend fun updateUser(
        newDisplayName: String,
        email: String,
        phoneNumber: String,
    ): Resource<Boolean> {
        return try {
            val profileUpdates = userProfileChangeRequest {
                displayName = newDisplayName
            }

            auth.currentUser?.updateProfile(profileUpdates)?.await()
            auth.currentUser?.updateEmail(email)?.await()

            val updatedValue = mapOf(
                "displayName" to newDisplayName,
                "email" to email,
                "phoneNumber" to phoneNumber
            )
            db.collection(USERS).document(auth.currentUser!!.uid).update(updatedValue)
                .addOnCompleteListener {
                    Log.w(TAG, "firestoreUpdateUser:Complete")
                }.addOnFailureListener {
                    Log.e(TAG, "firestoreUpdateUser:Error: $it ")
                }
            Log.w(TAG, "updateUser: Success")
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun updateProfilePhoto(
        newPhotoUri: Uri?
    ): Resource<Boolean> {
        return try {
            //Firebase Auth Update
            val photoUpdate = userProfileChangeRequest {
                photoUri = newPhotoUri
            }

            auth.currentUser?.updateProfile(photoUpdate)?.addOnCompleteListener {
                Log.w(TAG, "updateProfilePhoto:complete")
            }?.addOnFailureListener {
                Log.e(TAG, "updateProfilePhoto:error $it")
                Resource.Error(it)
            }?.await()
            newPhotoUri?.let { photoUri ->
                // Upload the new photo to Firebase Storage
                val storageRef = storage.reference

                /**
                 *  imageFileName = current user UUID + _profile_image.jpeg
                 *  imageRef = profile_images/imageFileName
                 * */
                val imageFileName = auth.currentUser!!.uid + IMAGE_FILE_SUFFIX
                val imageRef = storageRef.child("$PROFILE_IMAGES_FOLDER/$imageFileName")

                // Upload the image to storage
                val uploadTask = imageRef.putFile(photoUri)
                uploadTask
                    .addOnCompleteListener { Log.w(TAG, "uploadTask:Complete") }
                    .addOnFailureListener {
                        Log.e(TAG, "uploadTask:Error: $it")
                        Resource.Error(it)
                    }.await()

                // Get the download URL of the uploaded image
                val downloadUrl = imageRef.downloadUrl.await()

                // Update the user document in Firestore with the new photo URL
                val updatedPhoto = mapOf(
                    "photoUri" to downloadUrl.toString()
                )

                db.collection(USERS).document(auth.currentUser!!.uid).update(updatedPhoto)
                    .addOnCompleteListener { Log.w(TAG, "updateProfilePhoto:Success") }
                    .addOnFailureListener { Log.e(TAG, "updateProfilePhoto:Error: $it") }
            }

            Resource.Success(true)
        } catch (e: Exception) {
            Log.e(TAG, "updateProfilePhoto:Error: $e")
            Resource.Error(e)
        }
    }

    companion object {
        const val TAG = "profileRepo"
        const val PROFILE_IMAGES_FOLDER = "profile_images"
        const val IMAGE_FILE_SUFFIX = "_profile_image.jpeg"
        const val USERS = "users"
    }

}