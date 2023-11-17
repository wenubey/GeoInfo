package com.wenubey.countryapp.ui.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.utils.Constants.SEARCH_COUNTRIES_PLACEHOLDER
import java.util.Locale

@Composable
fun CountryDropdownMenu(
    countryData: Map<String?, String?>,
    onSelectCountryCode: (String?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    var currentValue by remember { mutableStateOf(countryData.keys.first()) }
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    placeholder = { Text(SEARCH_COUNTRIES_PLACEHOLDER) },
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
                            Icon(Icons.Default.Search, contentDescription = null)
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                )
                Divider(color = Color.Gray, thickness = 0.5.dp)
                filteredItems.value.onEachIndexed  { index, s ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        onClick = {
                            onSelectCountryCode(s.value)
                            currentValue = s.key
                            selectedIndex = index
                            expanded = false
                        },
                        text = {
                            Text(text = s.key ?: "")
                        }
                    )
                    Divider(color = Color.Gray, thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
private fun getFilteredItems(items: Map<String?, String?>, searchText: String): Map<String?,String?> {
    val normalizedSearchText = searchText.lowercase(Locale.getDefault())
    return if (searchText.isBlank()) {
        items
    } else {
        items.filterKeys { it?.lowercase(Locale.getDefault())?.contains(normalizedSearchText) == true }

    }
}