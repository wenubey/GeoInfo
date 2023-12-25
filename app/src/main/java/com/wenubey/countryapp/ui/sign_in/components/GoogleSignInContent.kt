package com.wenubey.countryapp.ui.sign_in.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.R
import com.wenubey.countryapp.ui.theme.CountryAppTheme


@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    oneTapSignIn: () -> Unit
) {
    Button(
        modifier = modifier, onClick = oneTapSignIn,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = GOOGLE_SIGN_UP
        )
        Text(
            text = GOOGLE_SIGN_UP,
            modifier = Modifier.padding(6.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun GoogleSignInContentPreview() {
    CountryAppTheme {
        Surface {
            GoogleSignInButton(oneTapSignIn = {})
        }
    }
}

private  const val GOOGLE_SIGN_UP = "Sign up with Google"