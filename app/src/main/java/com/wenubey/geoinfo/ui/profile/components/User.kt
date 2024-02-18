package com.wenubey.geoinfo.ui.profile.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.domain.model.User
import com.wenubey.geoinfo.ui.profile.ProfileViewModel
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.Constants.TAG
import com.wenubey.geoinfo.utils.Constants.UNDEFINED
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext


@Composable
fun User(
    user: User?,
    navigateToForgotPasswordScreen: (() -> Unit)? = null,
    navigateToCountryDetailScreen: (countryCode: String, countryName: String) -> Unit,
    navigateToSignInScreen: () -> Unit,
) {
    UserContent(
        user,
        navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
        navigateToCountryDetailScreen = navigateToCountryDetailScreen,
        navigateToSignInScreen = navigateToSignInScreen,
    )
}

@Suppress("CanBeVal")

@Composable
private fun UserContent(
    user: User? = null,
    profileViewModel: ProfileViewModel = koinViewModel(),
    navigateToSignInScreen: () -> Unit = {},
    navigateToForgotPasswordScreen: (() -> Unit)? = null,
    navigateToCountryDetailScreen: (countryCode: String, countryName: String) -> Unit = { _, _ -> },
) {

    val painter = rememberAsyncImagePainter(
        model = user?.photoUri, error = rememberVectorPainter(
            image = Icons.Default.AccountCircle
        ),
        onError = { error ->
            Log.e(TAG, "ProfileContent image load error: ${error.result.throwable}")
        }
    )
    var favCountries: Map<String, String>? by remember(user?.favCountries) {
        mutableStateOf(user?.favCountries)
    }


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
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                Box {
                    Image(
                        painter = painter,
                        contentDescription = stringResource(id = R.string.PROFILE_PHOTO_DESCRIPTION),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraLarge)
                            .size(100.dp)
                    )
                    ImagePicker(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        onImageSelected = {
                            profileViewModel.updateProfilePhoto(it)
                        },
                    )
                }


                AccountSettingsMenu(
                    modifier = Modifier.align(Alignment.TopEnd),
                    signOut = {
                        navigateToSignInScreen()
                        profileViewModel.signOut()
                    },
                    revokeAccess = {
                        navigateToSignInScreen()
                        profileViewModel.revokeAccess()
                    },
                    navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            UserFields(user = user)
            Spacer(modifier = Modifier.height(4.dp))
            FavCountriesRow(
                favCountries = favCountries,
                navigateToCountryDetailScreen = navigateToCountryDetailScreen,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavCountriesRow(
    favCountries: Map<String, String>?,
    navigateToCountryDetailScreen: (countryCode: String, countryName: String) -> Unit
) {
    Text(
        text = stringResource(id = R.string.FAV_COUNTRIES),
        style = MaterialTheme.typography.titleMedium
    )
    Divider(thickness = 2.dp, color = Color.Gray)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(favCountries?.toList() ?: emptyList()) { (countryFlag, countryFullName) ->
            val countryCode = countryFullName.split(" ").first()
            val countryName = countryFullName.split(" ").last()
            Card(
                onClick = {
                    navigateToCountryDetailScreen(countryCode, countryName)
                }
            ) {
                Text(text = countryFlag, style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}


@Composable
private fun UserFields(user: User?) {
    Column(
        modifier = Modifier
            .padding(4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        UserFieldRow(
            content = user?.displayName,
            imageVector = Icons.Outlined.Person,
            contentDescription = stringResource(id = R.string.DISPLAY_NAME_CONTENT_DESCRIPTION),
        )
        UserFieldRow(
            content = user?.email,
            imageVector = Icons.Outlined.Email,
            contentDescription = stringResource(id = R.string.EMAIL_CONTENT_DESCRIPTION),
        )
        UserFieldRow(
            content = user?.phoneNumber,
            imageVector = Icons.Outlined.Call,
            contentDescription = stringResource(id = R.string.EMAIL_CONTENT_DESCRIPTION),
        )
        UserFieldRow(
            content = user?.createdAt,
            imageVector = Icons.Outlined.AccessTime,
            contentDescription = stringResource(id = R.string.CREATED_AT_CONTENT_DESCRIPTION),
        )
    }
}

@Composable
private fun UserFieldRow(
    content: String?,
    imageVector: ImageVector,
    contentDescription: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
        Text(text = content ?: UNDEFINED, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun UserContentPreview() {
    GeoInfoAppTheme {
        KoinContext {
            Surface {
                UserContent()
            }
        }
    }
}

