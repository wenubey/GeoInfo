package com.wenubey.countryapp.utils.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.wenubey.countryapp.utils.Constants.EMAIL_LABEL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    email: TextFieldValue,
    onEmailValueChange: (email: TextFieldValue) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions()
) {

    OutlinedTextField(
        value = email,
        onValueChange = onEmailValueChange,
        label = { Text(text = EMAIL_LABEL) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        keyboardActions = keyboardActions
    )
}