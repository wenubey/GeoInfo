package com.wenubey.countryapp.ui.map.components.search_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.SortOption
import com.wenubey.countryapp.utils.SortOrder
import com.wenubey.countryapp.utils.toIcon

@Composable
fun CountrySearchBarSortButtons(
    query: String,
    onSortButtonClicked: (sortOption: SortOption, sortOrder: SortOrder, query: String) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        SortButton(
            sortOption = SortOption.NAME,
            onSortButtonClicked = { sortOption, sortOrder ->
                onSortButtonClicked(sortOption, sortOrder, query)
            })
        SortButton(
            sortOption = SortOption.AREA,
            onSortButtonClicked = { sortOption, sortOrder ->
                onSortButtonClicked(sortOption, sortOrder, query)
            })
        SortButton(
            sortOption = SortOption.POPULATION,
            onSortButtonClicked = { sortOption, sortOrder ->
                onSortButtonClicked(sortOption, sortOrder, query)
            })
    }
}

@Composable
fun SortButton(
    sortOption: SortOption,
    onSortButtonClicked: (SortOption, SortOrder) -> Unit
) {
    var sortOrder by remember {
        mutableStateOf(SortOrder.ASC)
    }
    OutlinedButton(
        onClick = {
            sortOrder = if (sortOrder == SortOrder.ASC) SortOrder.DESC else SortOrder.ASC
            onSortButtonClicked(sortOption, sortOrder)

        }
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(imageVector = sortOption.toIcon(), contentDescription = Constants.SORT_OPTION_ICON_DESCRIPTION)
            Icon(
                imageVector = if (sortOrder == SortOrder.ASC) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp,
                contentDescription = Constants.ASC_DESC_DESCRIPTION
            )
        }

    }
}