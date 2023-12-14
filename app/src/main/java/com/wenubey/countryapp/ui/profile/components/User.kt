package com.wenubey.countryapp.ui.profile.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.wenubey.countryapp.domain.model.User
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants


@Composable
fun User(
    user: User?
) {
    UserContent(user)
}

@Composable
private fun UserContent(
    user: User? = null
) {
    val painter = rememberAsyncImagePainter(
        model = user?.photoUrl, error = rememberVectorPainter(
            image = Icons.Default.AccountCircle
        ),
        onError = { error ->
            Log.e(Constants.TAG, "ProfileContent image load error: ${error.result.throwable}")
        }
    )
    val trimmedCreateData = user?.createdAt?.substringBefore("GMT")?.trim()
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = Constants.PROFILE_PHOTO_DESCRIPTION,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            //TODO get rid of hardcoded strings
            Text(text = "Username: " + user?.displayName)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Email: " + user?.email)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Phone Number: " + user?.phoneNumber)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Profile created at: $trimmedCreateData")
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun UserContentPreview() {
     CountryAppTheme {
        Surface {
             UserContent()
        }
    }
}
