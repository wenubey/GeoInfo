package com.wenubey.countryapp.utils.components

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.countryapp.ui.theme.CountryAppTheme


@Composable
fun PasswordTextField(
    password: TextFieldValue = previewPassword,
    onPasswordValueChange: (password: TextFieldValue, isError: Boolean) -> Unit = { _, _ -> },
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    isError = passwordVerifier(password)
    OutlinedTextField(
        value = password,
        onValueChange = {
            onPasswordValueChange(it, !passwordVerifier(it))
        },
        label = { Text(text = PASSWORD_LABEL, style = MaterialTheme.typography.bodySmall) },
        isError = isError,
        singleLine = true,
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        supportingText = {
            if (isError) {
                Text(text = PASSWORD_ERROR, style = MaterialTheme.typography.bodySmall)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            val icon = if (isPasswordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(imageVector = icon, contentDescription = PASSWORD_VISIBILITY_DESCRIPTION)
            }
        }
    )
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun PasswordTextFieldPreview() {
    CountryAppTheme {
        Surface {
            PasswordTextField()
        }
    }
}

private fun passwordVerifier(password: TextFieldValue): Boolean = !(password.text.length > 6 &&
        Regex("[!@#\$%^&*(),.?\":{}|<>]").containsMatchIn(password.text) &&
        Regex("[A-Z]").containsMatchIn(password.text) &&
        Regex("\\d").containsMatchIn(password.text)) && password.text.isNotBlank()

private val previewPassword = TextFieldValue("")
private const val PASSWORD_ERROR =
    "Password\n1.Must be longer than 6 characters \n2. Contain at least one special character\n3. Contain one capitalized character \n4. Contain one digit"
private const val PASSWORD_VISIBILITY_DESCRIPTION = "Password visibility on/off"
private const val PASSWORD_LABEL = "Password"