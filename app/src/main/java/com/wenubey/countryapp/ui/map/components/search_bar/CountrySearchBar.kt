package com.wenubey.countryapp.ui.map.components.search_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.SortOption
import com.wenubey.countryapp.utils.SortOrder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountrySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    countries: List<Country>,
    onSortButtonClicked: (sortOption: SortOption, sortOrder: SortOrder, query: String?) -> Unit,
    onCardClick: (countryCode: String?, countryName: String?) -> Unit,
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onQueryChange,
        active = active,
        onActiveChange = onActiveChange,
        placeholder = { Text(text = Constants.MAP_SEARCH_BAR_PLACEHOLDER) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        LazyColumn {
            item {
                CountrySearchBarSortButtons(
                    query = query,
                    onSortButtonClicked = onSortButtonClicked
                )
            }
            items(countries) { country ->
                CountrySearchResultCard(country = country, onCardClick = onCardClick)
            }
        }

    }
}




