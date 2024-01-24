package com.wenubey.geoinfo.ui.sign_in.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.theme.CountryAppTheme

@Composable
fun FacebookSignInButton(
    modifier: Modifier = Modifier,
    facebookSignInClicked: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = facebookSignInClicked
    ) {
        Image(painter = painterResource(id = R.drawable.ic_facebook_logo), contentDescription = stringResource(id= R.string.FACEBOOK_SIGN_UP))
        Text(text = stringResource(id= R.string.FACEBOOK_SIGN_UP), modifier = Modifier.padding(6.dp), style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun FacebookSignInButtonPreview() {
     CountryAppTheme {
        Surface {
             FacebookSignInButton(facebookSignInClicked = {})
        }
    }
}
