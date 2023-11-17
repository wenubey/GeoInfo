package com.wenubey.countryapp.ui.sign_in

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.wenubey.countryapp.domain.repository.auth.EmailAuthRepository
import com.wenubey.countryapp.domain.repository.auth.FacebookAuthRepository
import com.wenubey.countryapp.domain.repository.auth.GoogleSignInRepository
import com.wenubey.countryapp.domain.repository.auth.TwitterAuthRepository
import com.wenubey.countryapp.utils.Resource
import kotlinx.coroutines.launch

class SignInViewModel(
    private val emailAuthRepository: EmailAuthRepository,
    private val googleSignInRepository: GoogleSignInRepository,
    private val facebookAuthRepository: FacebookAuthRepository,
    private val twitterAuthRepository: TwitterAuthRepository,
    val oneTapClient: SignInClient,
) : ViewModel() {


    var isUserSigned by mutableStateOf<Resource<Boolean>>(Resource.Success(false))
        private set

    val isUserAuthenticated get() = googleSignInRepository.isUserAuthenticatedInFirebase

    var oneTapSignInResponse by mutableStateOf<Resource<BeginSignInResult>>(Resource.Success(null))
        private set


    fun oneTapSignIn() = viewModelScope.launch {

        oneTapSignInResponse = Resource.Loading

        oneTapSignInResponse = googleSignInRepository.oneTapSignInWithGoogle()
    }

    fun signInWithGoogle(googleCredential: AuthCredential) =viewModelScope.launch {
        isUserSigned = Resource.Loading

        isUserSigned = googleSignInRepository.firebaseSignInWithGoogle(googleCredential)
    }
    fun signInWithEmail(email: String, password: String) = viewModelScope.launch {
        isUserSigned = Resource.Loading

        isUserSigned = emailAuthRepository.signInWithEmailAndPassword(email, password)
    }

    fun signInWithFacebook(activity: Activity) = viewModelScope.launch {
        isUserSigned = Resource.Loading

        isUserSigned = facebookAuthRepository.signUpWithFacebook(activity)
    }

    fun signInWithTwitter(activity: Activity) = viewModelScope.launch {
        isUserSigned = Resource.Loading

        isUserSigned = twitterAuthRepository.signUpWithTwitter(activity = activity)
    }


}