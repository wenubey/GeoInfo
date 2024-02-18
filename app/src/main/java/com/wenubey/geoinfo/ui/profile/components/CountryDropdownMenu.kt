package com.wenubey.geoinfo.ui.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.fakeCountryCodeData
import java.util.Locale

@Composable
fun CountryDropdownMenu(
    countryData: Map<String?, String?>,
    currentCountryCode: String,
    onSelectCountryCode: (String?) -> Unit,
) {
    CountryDropdownMenuContent(
        countryData = countryData,
        currentCountryCode = currentCountryCode,
        onSelectCountryCode = onSelectCountryCode,
    )
}

@Composable
private fun CountryDropdownMenuContent(
    countryData: Map<String?, String?> = fakeCountryCodeData,
    currentCountryCode: String = fakeCountryCodeData.values.first()!!,
    onSelectCountryCode: (String?) -> Unit = {},
) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    val screenWidth = config.screenWidthDp
    var expanded by remember { mutableStateOf(false) }
    var currentValue by remember {
        mutableStateOf(countryData.filterValues { it!!.contains(currentCountryCode) }.keys.firstOrNull())
    }
    var searchText by remember { mutableStateOf("") }

    val filteredItems = rememberUpdatedState(getFilteredItems(countryData, searchText))
    Box {
        Row(
            modifier = Modifier.clickable {
                expanded = !expanded
            }
        ) {
            Text(text = currentValue ?: "")
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            LazyColumn(
                modifier = Modifier
                    .size(height = screenHeight / 3, width = (screenWidth / 1.5).dp)
                    .padding(4.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        placeholder = {
                            Text(
                                stringResource(id= R.string.SEARCH_COUNTRIES_PLACEHOLDER),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        trailingIcon = {
                            IconToggleButton(
                                checked = searchText.isNotEmpty(),
                                onCheckedChange = {
                                    searchText = if (it) "" else currentValue.orEmpty()
                                }
                            ) {
                                Icon(Icons.Default.Search, contentDescription = stringResource(id= R.string.SEARCH_COUNTRIES_PLACEHOLDER))
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                            keyboardType = KeyboardType.Text
                        ),
                        textStyle = MaterialTheme.typography.bodyMedium,
                    )
                }
                items(filteredItems.value.toList()) { (countryFlag, countryCode) ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        onClick = {
                            onSelectCountryCode(countryCode)
                            currentValue = countryCode
                            expanded = false
                        },
                        text = {
                            Text(
                                text = countryFlag ?: "",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    )
                    Divider(color = Color.Gray, thickness = 0.5.dp)
                }
            }
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun CountryDropDownMenuPreview() {
    GeoInfoAppTheme {
        Surface {
            CountryDropdownMenuContent()
        }
    }
}


@Composable
private fun getFilteredItems(
    items: Map<String?, String?>,
    searchText: String
): Map<String?, String?> {
    val normalizedSearchText = searchText.lowercase(Locale.getDefault())
    return if (searchText.isBlank()) {
        items
    } else {
        items.filterKeys {
            it?.lowercase(Locale.getDefault())?.contains(normalizedSearchText) == true
        }

    }
}