package com.wenubey.countryapp.ui.forgot_password.compoents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wenubey.countryapp.utils.Constants.RESET_PASSWORD
import com.wenubey.countryapp.utils.components.EmailTextField

@Composable
fun ForgotPasswordContent(
    paddingValues: PaddingValues,
    sendPasswordResetMail: (email: String) -> Unit,
    userEmail: String
) {
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(userEmail))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailTextField(email = email, onEmailValueChange = { email = it })
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { sendPasswordResetMail(email.text) }) {
            Text(text = RESET_PASSWORD, fontSize = 16.sp)
        }
    }
}