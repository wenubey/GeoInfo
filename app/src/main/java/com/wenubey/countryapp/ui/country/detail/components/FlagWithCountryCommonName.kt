package com.wenubey.countryapp.ui.country.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wenubey.countryapp.R
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.country.list.CountryEvent
import com.wenubey.countryapp.ui.deep_link.DeepLinkViewModel
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.fakeCountry
import org.koin.androidx.compose.koinViewModel

@Composable
fun FlagWithCountryCommonName(
    country: Country,
    navigateToMapScreen: (countryName: String?) -> Unit,
) {
    FlagAndHeaderContent(country = country, navigateToMapScreen = navigateToMapScreen)
}

@Composable
private fun FlagAndHeaderContent(
    country: Country,
    navigateToMapScreen: (countryName: String?) -> Unit,
    deepLinkViewModel: DeepLinkViewModel = koinViewModel(),
    countryViewModel: CountryViewModel = koinViewModel(),
) {
    val localConfig = LocalConfiguration.current
    val context = LocalContext.current

    val flagData = country.flag?.get("png")
    val coatOfArmsData = country.coatOfArms?.get("png")
    val flagIcon = Icons.Default.Flag
    val coatOfArmsIcon = Icons.Default.MilitaryTech

    var selectedImageData by remember {
        mutableStateOf(flagData)
    }
    var selectedIcon by remember {
        mutableStateOf(flagIcon)
    }

    val isMenuExpanded = remember {
        mutableStateOf(false)
    }

    fun changeFlagToCoatOfArms() {
        selectedImageData = if (selectedImageData == flagData) {
            coatOfArmsData
        } else {
            flagData
        }
        selectedIcon = if (selectedIcon == flagIcon) {
            coatOfArmsIcon
        } else {
            flagIcon
        }
    }

    fun goToWikipedia() {
        deepLinkViewModel.goToWikipedia(country.countryCommonName, context)
    }

    fun sendDeepLink() {
        deepLinkViewModel.sendDeepLink(
            countryCode = country.countryCodeCCA2 ?: "",
            countryName = country.countryCommonName ?: "",
            context = context
        )
    }

    fun updateFavCountry(isFavorite: Boolean) {
        countryViewModel.onEvent(
            CountryEvent.OnUserUpdateFavorite(country, isFavorite)
        )
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(selectedImageData)
            .build(),
        placeholder = rememberVectorPainter(image = Icons.Default.Flag),
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlagWithCoatOfArms(
            localConfig = localConfig,
            painter = painter,
            onChangeFlagToCoatOfArms = { changeFlagToCoatOfArms() },
            country = country
        )
        CountryName(
            country = country,
            isMenuExpanded = isMenuExpanded,
            selectedIcon = selectedIcon,
            onChangeFlagToCoatOfArms = { changeFlagToCoatOfArms() })
        Divider(thickness = 1.dp)

        DetailDropDownMenu(
            isMenuExpanded = isMenuExpanded,
            goToWikipedia = { goToWikipedia() },
            navigateToMapScreen = { navigateToMapScreen(country.countryCommonName) },
            shareCountryInfo = { sendDeepLink() },
            updateFavCountry = { isFavorite ->
                updateFavCountry(isFavorite) },
            isFavorite = country.isFavorite
        )
    }
}

@Composable
private fun FlagWithCoatOfArms(
    localConfig: Configuration = Configuration(),
    painter: AsyncImagePainter = rememberAsyncImagePainter(
        model = "",
        placeholder = rememberVectorPainter(image = Icons.Default.Flag)
    ),
    onChangeFlagToCoatOfArms: () -> Unit = {},
    country: Country = fakeCountry,
) {
    val screenWidth = localConfig.screenWidthDp
    val screeHeight = localConfig.screenHeightDp
    Image(
        modifier = Modifier
            .size(
                width = screenWidth.dp,
                height = (screeHeight / 3).dp
            )
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onChangeFlagToCoatOfArms,
            ),
        contentScale = ContentScale.Fit,
        painter = painter,
        contentDescription = country.flag?.get("alt")
            ?: Constants.COUNTRY_FLAG_CONTENT_DESCRIPTION,
    )
}

@Composable
private fun CountryName(
    country: Country = fakeCountry,
    isMenuExpanded: MutableState<Boolean> = mutableStateOf(false),
    selectedIcon: ImageVector = Icons.Default.MilitaryTech,
    onChangeFlagToCoatOfArms: () -> Unit = {},
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.weight(0.7f),
            text = country.countryOfficialName ?: Constants.UNDEFINED,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
            overflow = TextOverflow.Clip
        )
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.weight(0.25f)) {
            IconButton(onClick = onChangeFlagToCoatOfArms) {
                Icon(
                    imageVector = selectedIcon,
                    contentDescription = Constants.COUNTRY_SELECTED_ICON_DESCRIPTION
                )
            }
            IconButton(onClick = { isMenuExpanded.value = !isMenuExpanded.value }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = Constants.COUNTRY_DROPDOWN_MENU_CONTENT_DESCRIPTION
                )
            }
        }
    }
}


@Composable
private fun DetailDropDownMenu(
    isMenuExpanded: MutableState<Boolean> = mutableStateOf(true),
    goToWikipedia: () -> Unit = {},
    navigateToMapScreen: () -> Unit = {},
    shareCountryInfo: () -> Unit = {},
    updateFavCountry: (isFavorite: Boolean) -> Unit = {},
    isFavorite: Boolean = false,
) {
    var countryIsFavorite by remember {
        mutableStateOf(isFavorite)
    }

    DropdownMenu(
        expanded = isMenuExpanded.value,
        onDismissRequest = { isMenuExpanded.value = false },
        offset = DpOffset(x = (-16).dp, y = 0.dp)
    ) {
        DropdownMenuItem(
            text = {
                Row {
                    Image(
                        modifier = Modifier
                            .size(25.dp),
                        painter = painterResource(id = R.drawable.wikipedia_logo),
                        contentDescription = Constants.WIKIPEDIA_CONTENT_DESCRIPTION,
                    )
                    Text(
                        text = Constants.MORE_INFO_WIKIPEDIA,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            onClick = {
                goToWikipedia()
                isMenuExpanded.value = false
            },
        )
        DropdownMenuItem(
            text = {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Image(
                        imageVector = Icons.Default.PinDrop,
                        contentDescription = Constants.COUNTRY_MAP_CONTENT_DESCRIPTION,
                        colorFilter = ColorFilter.tint(LocalContentColor.current)
                    )
                    Text(text = Constants.GO_TO_COUNTRY_LOCATION)
                }
            },
            onClick = {
                navigateToMapScreen()
                isMenuExpanded.value = false
            },
        )
        DropdownMenuItem(text = {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Image(
                    imageVector = Icons.Default.Share,
                    contentDescription = Constants.SHARE_CONTENT_DESCRIPTION
                )
                Text(text = Constants.SHARE)
            }
        }, onClick = { shareCountryInfo() })
        DropdownMenuItem(
            text = {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Image(
                        imageVector = if (countryIsFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = Constants.ADD_REMOVE_FAV_CONTENT_DESCRIPTION
                    )
                    Text(text = Constants.FAVORITE)
                }
            },
            onClick = {
                countryIsFavorite = !countryIsFavorite
                updateFavCountry(countryIsFavorite)
            },
        )
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun CountryNamePreview() {
    CountryAppTheme {
        Surface {
            CountryName()
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun DetailDropDownMenuPreview() {
    CountryAppTheme {
        Surface {
            DetailDropDownMenu()
        }
    }
}