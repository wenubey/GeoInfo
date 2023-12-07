package com.wenubey.countryapp.ui.profile

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.email_verify.components.RevokeAccess
import com.wenubey.countryapp.ui.profile.components.ProfileContent
import com.wenubey.countryapp.ui.profile.components.ProfileTopBar
import com.wenubey.countryapp.ui.profile.components.UpdateUser
import com.wenubey.countryapp.ui.profile.components.UserInfoUpdateDialog
import com.wenubey.countryapp.ui.profile.components.UserUpdateFAB
import com.wenubey.countryapp.utils.Constants.PROFILE_SCREEN_TITLE
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navigateBack: () -> Unit,
    navigateToForgotPasswordScreen: (email: String) -> Unit,
    profileViewModel: ProfileViewModel = koinViewModel(),
    countryViewModel: CountryViewModel = koinViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val showDialog = remember { mutableStateOf(false) }
    var email by remember { mutableStateOf(TextFieldValue(profileViewModel.currentUser?.email ?: "")) }
    var displayName by remember {
        mutableStateOf(
            TextFieldValue(
                profileViewModel.currentUser?.displayName ?: ""
            )
        )
    }
    var countryPhoneCode by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf(
            profileViewModel.currentUser?.phoneNumber ?: ""
        )
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ProfileTopBar(
                title = PROFILE_SCREEN_TITLE,
                signOut = { profileViewModel.signOut() },
                revokeAccess = { profileViewModel.revokeAccess() },
                navigateBack = navigateBack,
                navigateToForgotPasswordScreen = {
                    navigateToForgotPasswordScreen(email.text)
                },
            )
        },
        content = { paddingValues ->
            ProfileContent(paddingValues = paddingValues, user = profileViewModel.currentUserDataResponse)
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        floatingActionButton = {
            UserUpdateFAB(onClick = { showDialog.value = !showDialog.value })
        }
    )

    if (showDialog.value) {
        UserInfoUpdateDialog(
            showDialog = showDialog,
            onClickConfirm = {
                profileViewModel.updateUser(
                    newDisplayName = displayName.text,
                    email = email.text,
                    phoneNumber = "$countryPhoneCode $phoneNumber"
                )
                showDialog.value = false
            },
            email = email,
            displayName = displayName,
            phoneNumber = phoneNumber,
            onEmailValueChange = { email = it },
            onDisplayNameValueChange = { displayName = it },
            onPhoneNumberValueChange = { phoneNumber = it },
            countryCodeMap = countryViewModel.countryPhoneCodes,
            onPhoneCodeValueChange = {countryPhoneCode = it!!}

        )
    }

    RevokeAccess(
        viewModel = profileViewModel,
        snackBarHostState = snackBarHostState,
        coroutineScope = coroutineScope
    )

    UpdateUser(
        viewModel = profileViewModel,
        snackBarHostState = snackBarHostState
    )
}