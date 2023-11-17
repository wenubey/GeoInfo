package com.wenubey.countryapp.ui.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun PhonePicker(
    countryData: Map<String?,String?>,
    onSelectCountryCode: (String?) -> Unit,
    phoneNumber: String,
    onPhoneCodeValueChange: (phoneCode: String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountryDropdownMenu(
            countryData = countryData,
            onSelectCountryCode = onSelectCountryCode,
        )
        OutlinedTextField(value = phoneNumber, onValueChange = onPhoneCodeValueChange)
    }
}