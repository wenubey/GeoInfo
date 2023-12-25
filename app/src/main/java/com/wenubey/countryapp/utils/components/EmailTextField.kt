package com.wenubey.countryapp.utils.components

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.countryapp.ui.theme.CountryAppTheme

@Composable
fun EmailTextField(
    email: TextFieldValue = previewEmail,
    onEmailValueChange: (email: TextFieldValue, isError: Boolean) -> Unit = { _, _ ->},
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    var isError by remember {
        mutableStateOf(false)
    }
    isError = emailVerifier(email)
    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailValueChange(it, !emailVerifier(it))
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        label = { Text(text = EMAIL_LABEL, style = MaterialTheme.typography.bodySmall) },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = EMAIL_ERROR, style = MaterialTheme.typography.bodySmall)
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        keyboardActions = keyboardActions
    )
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun EmailTextFieldPreview() {
    CountryAppTheme {
        Surface {
            EmailTextField()
        }
    }
}

private fun emailVerifier(email: TextFieldValue): Boolean = !(email.text.contains("@") && email.text.contains(".com")) && email.text.isNotBlank()

private val previewEmail = TextFieldValue("helloworld@helloword.com")
private const val EMAIL_ERROR = "Email account must contain '@' and '.com'"
private const val EMAIL_LABEL = "Email"