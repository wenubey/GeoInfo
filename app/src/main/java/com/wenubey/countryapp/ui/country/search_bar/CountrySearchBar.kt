package com.wenubey.countryapp.ui.country.search_bar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.SortOption
import com.wenubey.countryapp.utils.SortOrder
import com.wenubey.countryapp.utils.fakeCountry
import com.wenubey.countryapp.utils.toIcon


@Composable
fun CountrySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    countries: List<Country>,
    onSortButtonClicked: (sortOption: SortOption, sortOrder: SortOrder, query: String?, isFavorite: Int) -> Unit,
    onCardClick: (countryCode: String?, countryName: String?) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit,
    isFavorite: Int,
) {
    SearchBarContent(
        query = query,
        onQueryChange = onQueryChange,
        countries = countries,
        onSortButtonClicked = onSortButtonClicked,
        onCardClick = onCardClick,
        onFavoriteButtonClicked = onFavoriteButtonClicked,
        isFavorite = isFavorite
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBarContent(
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    countries: List<Country> = listOf(fakeCountry),
    onSortButtonClicked: (sortOption: SortOption, sortOrder: SortOrder, query: String?, isFavorite: Int) -> Unit = { _, _, _, _ -> },
    onCardClick: (countryCode: String?, countryName: String?) -> Unit = { _, _ -> },
    isFavorite: Int = 0,
    onFavoriteButtonClicked: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onQueryChange,
            active = false,
            onActiveChange = {},
            placeholder = { Text(text = Constants.MAP_SEARCH_BAR_PLACEHOLDER) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {

        }

        LazyColumn {
            item {
                CountrySearchBarSortButtons(
                    query = query,
                    onSortButtonClicked = onSortButtonClicked,
                    onFavoriteButtonClicked = onFavoriteButtonClicked,
                    isFavorite = isFavorite
                )
            }
            items(countries) { country ->
                CountrySearchResultCard(country = country, onCardClick = onCardClick)
            }
        }


    }
}


@Composable
private fun CountrySearchBarSortButtons(
    query: String,
    isFavorite: Int,
    onSortButtonClicked: (sortOption: SortOption, sortOrder: SortOrder, query: String, isFavorite: Int) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit
) {

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        SortButton(
            sortOption = SortOption.NAME,
            onSortButtonClicked = { sortOption, sortOrder ->
                onSortButtonClicked(sortOption, sortOrder, query, isFavorite)
            })
        SortButton(
            sortOption = SortOption.AREA,
            onSortButtonClicked = { sortOption, sortOrder ->
                onSortButtonClicked(sortOption, sortOrder, query, isFavorite)
            })
        SortButton(
            sortOption = SortOption.POPULATION,
            onSortButtonClicked = { sortOption, sortOrder ->
                onSortButtonClicked(sortOption, sortOrder, query, isFavorite)
            })
        OutlinedButton(
            onClick = {
                val newQuery: Int = if (isFavorite == 0) {
                    1
                } else{
                    0
                }
                onFavoriteButtonClicked(newQuery)
            }
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(
                    imageVector = if (isFavorite == 0) Icons.Outlined.StarBorder else Icons.Filled.Star,
                    contentDescription = Constants.SORT_OPTION_ICON_DESCRIPTION
                )
            }

        }
    }
}

@Composable
private fun SortButton(
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
            Icon(
                imageVector = sortOption.toIcon(),
                contentDescription = Constants.SORT_OPTION_ICON_DESCRIPTION
            )
            Icon(
                imageVector = if (sortOrder == SortOrder.ASC) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp,
                contentDescription = Constants.ASC_DESC_DESCRIPTION
            )
        }

    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun SearchBarContentPreview() {
    CountryAppTheme {
        Surface {
            SearchBarContent()
        }
    }
}




