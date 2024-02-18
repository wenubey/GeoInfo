package com.wenubey.geoinfo.utils.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.emailVerifier

@Composable
fun EmailTextField(
    email: TextFieldValue = TextFieldValue(stringResource(id= R.string.PREVIEW_EMAIL)),
    onEmailValueChange: (email: TextFieldValue, isError: Boolean) -> Unit = { _, _ ->},
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    var isError by remember {
        mutableStateOf(false)
    }
    isError = email.emailVerifier()
    OutlinedTextField(
        modifier = Modifier.testTag(EMAIL_TEXT_FIELD_TEST_TAG),
        value = email,
        onValueChange = {
            onEmailValueChange(it, !email.emailVerifier())
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        label = { Text(text = stringResource(id= R.string.EMAIL_LABEL), style = MaterialTheme.typography.bodySmall) },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = stringResource(id= R.string.EMAIL_ERROR), style = MaterialTheme.typography.bodySmall)
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
    GeoInfoAppTheme {
        Surface {
            EmailTextField()
        }
    }
}

const val EMAIL_TEXT_FIELD_TEST_TAG = "email_text_field_tag"