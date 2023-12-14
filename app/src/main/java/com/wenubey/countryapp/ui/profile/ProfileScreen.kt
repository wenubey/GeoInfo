package com.wenubey.countryapp.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.wenubey.countryapp.ui.profile.components.AccountSettingsMenu
import com.wenubey.countryapp.ui.profile.components.UpdateUser
import com.wenubey.countryapp.ui.profile.components.User
import com.wenubey.countryapp.ui.profile.components.UserInfoUpdateDialog
import com.wenubey.countryapp.ui.profile.components.UserUpdateFAB
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navigateToForgotPasswordScreen: (email: String) -> Unit,
    snackBarHostState: SnackbarHostState,
    paddingValues: PaddingValues
) {
    ProfileScreenContent(
        navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
        snackBarHostState = snackBarHostState,
        paddingValues = paddingValues
    )
}


@Composable
private fun ProfileScreenContent(
    navigateToForgotPasswordScreen: (email: String) -> Unit,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    profileViewModel: ProfileViewModel = koinViewModel(),
    countryViewModel: CountryViewModel = koinViewModel(),
    paddingValues: PaddingValues = PaddingValues()

) {
    val showDialog = remember { mutableStateOf(false) }
    var email by remember {
        mutableStateOf(
            TextFieldValue(
                profileViewModel.currentUser?.email ?: ""
            )
        )
    }
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues), contentAlignment = Alignment.Center
    ) {
        User(user = profileViewModel.currentUserDataResponse)
        UserUpdateFAB(
            onClick = { showDialog.value = !showDialog.value },
            modifier = Modifier
                .align(Alignment.BottomEnd),
        )
        AccountSettingsMenu(
            modifier = Modifier.align(Alignment.TopEnd),
            signOut = { profileViewModel.signOut() },
            revokeAccess = { profileViewModel.revokeAccess() },
            navigateToForgotPasswordScreen = {
                navigateToForgotPasswordScreen(email.text)
            },
        )
    }
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
            onPhoneCodeValueChange = { countryPhoneCode = it!! }

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

