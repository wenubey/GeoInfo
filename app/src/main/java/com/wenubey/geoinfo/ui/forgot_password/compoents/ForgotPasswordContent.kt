package com.wenubey.geoinfo.ui.forgot_password.compoents

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.theme.CountryAppTheme
import com.wenubey.geoinfo.utils.components.EmailTextField

@Composable
fun ForgotPasswordContent(
    paddingValues: PaddingValues = PaddingValues(),
    sendPasswordResetMail: (email: String) -> Unit = {},
    userEmail: String = ""
) {
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(userEmail))
    }
    var isButtonEnabled by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailTextField(
            email = email,
            onEmailValueChange = { newEmail, isError ->
                email = newEmail
                isButtonEnabled = isError
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            enabled = isButtonEnabled,
            onClick = { sendPasswordResetMail(email.text) },
        ) {
            Text(text = stringResource(id= R.string.RESET_PASSWORD), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun ForgotPasswordContentPreview() {
     CountryAppTheme {
        Surface {
             ForgotPasswordContent()
        }
    }
}
