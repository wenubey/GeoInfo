package com.wenubey.countryapp.ui.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants.DISPLAY_NAME_LABEL
import com.wenubey.countryapp.utils.Constants.PROFILE_INFO
import com.wenubey.countryapp.utils.Constants.SAVE
import com.wenubey.countryapp.utils.components.EmailTextField


@Composable
fun UserInfoUpdateDialog(
    showDialog: MutableState<Boolean>,
    email: TextFieldValue,
    phoneNumber: String,
    onEmailValueChange: (email: TextFieldValue) -> Unit,
    displayName: TextFieldValue,
    onDisplayNameValueChange: (displayName: TextFieldValue) -> Unit,
    onPhoneNumberValueChange: (phoneNumber: String) -> Unit,
    onPhoneCodeValueChange: (phoneCode: String?) -> Unit,
    onClickConfirm: () -> Unit,
    countryCodeMap: Map<String?, String?>
) {
    AlertDialogContent(
        showDialog = showDialog,
        email = email,
        phoneNumber = phoneNumber,
        onEmailValueChange = onEmailValueChange,
        displayName = displayName,
        onDisplayNameValueChange = onDisplayNameValueChange,
        onPhoneNumberValueChange = onPhoneNumberValueChange,
        onPhoneCodeValueChange = onPhoneCodeValueChange,
        onClickConfirm = onClickConfirm,
        countryCodeMap = countryCodeMap
    )
}

@Composable
private fun AlertDialogContent(
    showDialog: MutableState<Boolean> = mutableStateOf(false),
    email: TextFieldValue = TextFieldValue("email"),
    phoneNumber: String = "",
    onEmailValueChange: (email: TextFieldValue) -> Unit = {},
    displayName: TextFieldValue = TextFieldValue("displayName"),
    onDisplayNameValueChange: (displayName: TextFieldValue) -> Unit = {},
    onPhoneNumberValueChange: (phoneNumber: String) -> Unit = {},
    onPhoneCodeValueChange: (phoneCode: String?) -> Unit = {},
    onClickConfirm: () -> Unit = {},
    countryCodeMap: Map<String?, String?> = mapOf("PL" to "+48")
) {
    val focusManager = LocalFocusManager.current

    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text(text = PROFILE_INFO, style = MaterialTheme.typography.titleMedium) },
        text = {
            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailTextField(email = email,
                    onEmailValueChange = onEmailValueChange,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = displayName,
                    onValueChange = onDisplayNameValueChange,
                    label = {
                        Text(
                            text = DISPLAY_NAME_LABEL,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
                PhonePicker(
                    countryData = countryCodeMap,
                    onSelectCountryCode = onPhoneCodeValueChange,
                    phoneNumber = phoneNumber,
                    onPhoneCodeValueChange = onPhoneNumberValueChange
                )

            }
        },
        confirmButton = {
            Button(
                onClick = onClickConfirm
            ) {
                Text(text = SAVE, style = MaterialTheme.typography.bodyMedium)
            }
        },
    )
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun AlertDialogContentPreview() {
     CountryAppTheme {
        Surface {
             AlertDialogContent()
        }
    }
}