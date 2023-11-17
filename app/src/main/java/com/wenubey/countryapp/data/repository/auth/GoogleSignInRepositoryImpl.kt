package com.wenubey.countryapp.data.repository.auth

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wenubey.countryapp.domain.repository.auth.GoogleSignInRepository
import com.wenubey.countryapp.utils.AuthProvider
import com.wenubey.countryapp.utils.Constants.SIGN_IN_REQUEST
import com.wenubey.countryapp.utils.Constants.SIGN_UP_REQUEST
import com.wenubey.countryapp.utils.Resource
import com.wenubey.countryapp.utils.addUserToFirestore
import com.wenubey.countryapp.utils.getCurrentTime
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
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
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
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                addUserToFirestore(db = db, auth = auth, authProvider = AuthProvider.GOOGLE, createdAt = getCurrentTime())
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}