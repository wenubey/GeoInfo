package com.wenubey.geoinfo.ui.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun PhonePicker(
    countryData: Map<String?,String?>,
    onSelectCountryCode: (String?) -> Unit,
    countryCode: String,
    phoneNumberBody: TextFieldValue,
    onPhoneCodeValueChange: (phoneCode: TextFieldValue) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountryDropdownMenu(
            countryData = countryData,
            onSelectCountryCode = onSelectCountryCode,
            currentCountryCode = countryCode
        )
        OutlinedTextField(value = phoneNumberBody, onValueChange = onPhoneCodeValueChange)
    }
}

