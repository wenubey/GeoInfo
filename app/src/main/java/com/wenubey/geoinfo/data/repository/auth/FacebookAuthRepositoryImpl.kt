package com.wenubey.geoinfo.data.repository.auth

import android.app.Activity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.wenubey.geoinfo.domain.repository.auth.FacebookAuthRepository
import com.wenubey.geoinfo.utils.AuthProvider
import com.wenubey.geoinfo.utils.Resource
import com.wenubey.geoinfo.utils.addUserToFirestore
import com.wenubey.geoinfo.utils.getCurrentTime

class FacebookAuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : FacebookAuthRepository {

    private val callbackManager = CallbackManager.Factory.create()
    private val loginManager = LoginManager.getInstance()
    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun signUpWithFacebook(activity: Activity): Resource<Boolean> {
        return try {

            loginManager.logInWithReadPermissions(activity, listOf("public_profile", "email"))
            loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Resource.Error(Exception(CANCEL_OPERATION))
                }

                override fun onError(error: FacebookException) {
                    Resource.Error(error)
                }

                override fun onSuccess(result: LoginResult) {
                    val token = result.accessToken.token
                    val credential = FacebookAuthProvider.getCredential(token)
                    val authResult = auth.signInWithCredential(credential).result
                    val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
                    if (isNewUser) {
                        addUserToFirestore(
                            db = db, auth = auth,
                            authProvider = AuthProvider.FACEBOOK,
                            createdAt = getCurrentTime()
                        )
                    }
                }
            })
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

   companion object {
       private const val CANCEL_OPERATION = "You canceled the operation"
   }
}