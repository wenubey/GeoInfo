package com.wenubey.geoinfo.utils.components

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.passwordVerifier


@Composable
fun PasswordTextField(
    password: TextFieldValue = TextFieldValue(stringResource(id = R.string.preview_password)),
    onPasswordValueChange: (password: TextFieldValue, isError: Boolean) -> Unit = { _, _ -> },
    isPasswordVisible: MutableState<Boolean> = mutableStateOf(false),
) {
    var isError by remember { mutableStateOf(false) }
    isError = password.passwordVerifier()
    OutlinedTextField(
        modifier = Modifier.testTag(stringResource(id = R.string.password_text_field_test_tag)),
        value = password,
        onValueChange = {
            onPasswordValueChange(it, !password.passwordVerifier())
        },
        label = {
            Text(
                text = stringResource(id = R.string.password_label),
                style = MaterialTheme.typography.bodySmall
            )
        },
        isError = isError,
        singleLine = true,
        visualTransformation = if (isPasswordVisible.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        supportingText = {
            if (isError) {
                Text(
                    text = stringResource(id = R.string.password_error),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            val icon = if (isPasswordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            IconButton(onClick = { isPasswordVisible.value = !isPasswordVisible.value }) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(id = R.string.password_visibility_description)
                )
            }
        }
    )
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun PasswordTextFieldPreview() {
    GeoInfoAppTheme {
        Surface {
            PasswordTextField()
        }
    }
}
