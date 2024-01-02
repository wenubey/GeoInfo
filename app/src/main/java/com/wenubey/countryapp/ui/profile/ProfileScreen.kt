package com.wenubey.countryapp.ui.profile

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.email_verify.components.RevokeAccess
import com.wenubey.countryapp.ui.profile.components.UpdateUser
import com.wenubey.countryapp.ui.profile.components.User
import com.wenubey.countryapp.ui.profile.components.UserInfoUpdateDialog
import com.wenubey.countryapp.ui.profile.components.UserUpdateFAB
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants.TAG
import com.wenubey.countryapp.utils.components.ProgressBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navigateToForgotPasswordScreen: (email: String) -> Unit,
    snackBarHostState: SnackbarHostState,
    navigateToCountryDetailScreen: (countryCode: String, countryName: String) -> Unit,
    navigateToSignInScreen: () -> Unit,
) {
    ProfileScreenContent(
        navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
        snackBarHostState = snackBarHostState,
        navigateToCountryDetailScreen = navigateToCountryDetailScreen,
        navigateToSignInScreen = navigateToSignInScreen
    )
}


@Composable
private fun ProfileScreenContent(
    navigateToForgotPasswordScreen: (email: String) -> Unit,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    profileViewModel: ProfileViewModel = koinViewModel(),
    countryViewModel: CountryViewModel = koinViewModel(),
    navigateToCountryDetailScreen: (countryCode: String, countryName: String) -> Unit = { _, _ ->},
    navigateToSignInScreen: () -> Unit = {}
) {
    val user = profileViewModel.currentUserDataResponse
    val showDialog = remember { mutableStateOf(false) }
    var email by remember(user) {
        mutableStateOf(
            TextFieldValue(user?.email ?: "")
        )
    }
    var displayName by remember(user) {
        mutableStateOf(
            TextFieldValue(user?.displayName ?: "")
        )
    }
    val phoneNumber by remember(user) {
        mutableStateOf(user?.phoneNumber ?: "")
    }
    var phoneNumberBody by remember(user) {
        mutableStateOf(TextFieldValue(extractPhoneBody(phoneNumber)))
    }
    var countryPhoneCode by remember(user) {
        mutableStateOf(extractPhoneCode(phoneNumber = phoneNumber))
    }
    val coroutineScope = rememberCoroutineScope()
    if (user == null) {
        ProgressBar()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            UserUpdateFAB(
                onClick = { showDialog.value = !showDialog.value },
                modifier = Modifier,
            )
        }
    ) { paddingValue ->
        Column(modifier = Modifier
            .padding(paddingValue)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            User(
                user = profileViewModel.currentUserDataResponse,
                navigateToForgotPasswordScreen = { navigateToForgotPasswordScreen(email.text) },
                navigateToCountryDetailScreen = navigateToCountryDetailScreen,
                navigateToSignInScreen = navigateToSignInScreen
            )
        }

    }
    if (showDialog.value) {
        UserInfoUpdateDialog(
            showDialog = showDialog,
            onClickConfirm = {
                profileViewModel.updateUser(
                    newDisplayName = displayName.text,
                    email = email.text,
                    phoneNumber = "$countryPhoneCode ${phoneNumberBody.text}"
                )
                showDialog.value = false
            },
            email = email,
            displayName = displayName,
            phoneNumberBody = phoneNumberBody,
            onEmailValueChange = { email = it },
            onDisplayNameValueChange = { displayName = it },
            onPhoneNumberValueChange = { phoneNumberBody = it },
            countryCodeMap = countryViewModel.countryPhoneCodes,
            onCountryCodeValueChange = { countryPhoneCode = it!! },
            countryCode = countryPhoneCode,
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


@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun ProfileScreenContentPreview() {
    CountryAppTheme {
        Surface {
            ProfileScreenContent(
                navigateToForgotPasswordScreen = {},
            )
        }
    }
}

private fun extractPhoneBody(phoneNumber: String): String = phoneNumber.substringAfterLast(" ")
private fun extractPhoneCode(phoneNumber: String): String = phoneNumber.substringBefore(" ")



