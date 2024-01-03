package com.wenubey.countryapp.ui.country.list

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.R
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.country.CountryEvent
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.SortOption
import com.wenubey.countryapp.utils.SortOrder
import com.wenubey.countryapp.utils.components.ErrorScreen
import com.wenubey.countryapp.utils.components.ProgressBar
import com.wenubey.countryapp.utils.fakeCountry
import com.wenubey.countryapp.utils.toIcon
import org.koin.androidx.compose.koinViewModel


@Composable
fun CountryListScreen(
    navigateToCountryDetailScreen: (countryCode: String?, countryName: String?) -> Unit,
    countryViewModel: CountryViewModel = koinViewModel(),
) {
    val uiState = countryViewModel.countryListDataState
    if (uiState.isLoading) {
        ProgressBar()
    } else if (uiState.countries != null) {
        val countries = uiState.countries
        CountryListContent(
            countries = countries,
            navigateToCountryDetailScreen = navigateToCountryDetailScreen
        )
    } else if (uiState.error != null) {
        ErrorScreen(error = uiState.error)
    }
}

@Composable
private fun CountryListContent(
    countries: List<Country> = listOf(fakeCountry),
    navigateToCountryDetailScreen: (countryCode: String?, countryName: String?) -> Unit = { _, _ -> },
    countryViewModel: CountryViewModel = koinViewModel(),

    ) {

    CountryList(
        query = countryViewModel.searchQuery.value,
        onQueryChange = {
            countryViewModel.onEvent(
                CountryEvent.OnSearchQueryChange(it)
            )
        },
        countries = countries,
        onSortButtonClicked = { sortOption, sortOrder, query ->
            onSortButtonClicked(sortOption, sortOrder, query, countryViewModel)
        },
        onCardClick = navigateToCountryDetailScreen,
        onFavButtonClicked = { country, countryUpdatedFav ->
            countryViewModel.onEvent(
                CountryEvent.OnUserUpdateFavorite(country, countryUpdatedFav)
            )
        }
    )

}

@Composable
private fun CountryList(
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    countries: List<Country> = listOf(fakeCountry),
    onSortButtonClicked: (sortOption: SortOption, sortOrder: SortOrder, query: String?) -> Unit = { _, _, _ -> },
    onCardClick: (countryCode: String?, countryName: String?) -> Unit = { _, _ -> },
    onFavButtonClicked: (country: Country, countryUpdatedFav: Boolean) -> Unit = { _, _ -> },
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        CountrySearchBar(query = query, onQueryChange = onQueryChange)
        CountryListSortButtons(
            query = query,
            onSortButtonClicked = onSortButtonClicked,
        )
        LazyColumn {
            items(countries) { country ->
                CountryListCard(
                    country = country,
                    onCardClick = onCardClick,
                    onFavButtonClicked = onFavButtonClicked
                )
            }
        }
    }
}

@Composable
private fun CountrySearchBar(query: String = "", onQueryChange: (String) -> Unit = {}) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = stringResource(id = R.string.MAP_SEARCH_BAR_PLACEHOLDER),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        trailingIcon = {
            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
        }
    )
}

@Composable
private fun CountryListSortButtons(
    query: String,
    onSortButtonClicked: (sortOption: SortOption, sortOrder: SortOrder, query: String) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
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
        SortButton(sortOption = SortOption.FAV, onSortButtonClicked = { sortOption, sortOrder ->

            onSortButtonClicked(sortOption, sortOrder, query)
        })
    }
}

@Composable
private fun SortButton(
    sortOption: SortOption = SortOption.FAV,
    onSortButtonClicked: (SortOption, SortOrder) -> Unit = { _, _ -> }
) {
    var sortOrder by remember {
        mutableStateOf(SortOrder.ASC)
    }
    OutlinedButton(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        onClick ={
            sortOrder = if (sortOrder == SortOrder.ASC) SortOrder.DESC else SortOrder.ASC
            onSortButtonClicked(sortOption, sortOrder)
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = sortOption.toIcon(),
                contentDescription = stringResource(id = R.string.SORT_OPTION_ICON_DESCRIPTION)
            )
            Icon(
                imageVector = if (sortOrder == SortOrder.ASC) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp,
                contentDescription = stringResource(id = R.string.ASC_DESC_DESCRIPTION)
            )
        }

    }
}

private fun onSortButtonClicked(
    sortOption: SortOption,
    sortOrder: SortOrder,
    query: String?,
    viewModel: CountryViewModel
) {
    if (!query.isNullOrBlank()) {
        viewModel.onEvent(
            CountryEvent.OnGetAllCountriesFilteredAndSorted(
                query = query,
                sortOrder = sortOrder,
                sortOption = sortOption,
            )
        )
    } else {
        viewModel.onEvent(
            CountryEvent.OnSortButtonClick(
                sortOption = sortOption,
                sortOrder = sortOrder,
            )
        )
    }
}

@Preview(name = "Dark mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun SortButtonPreview() {
    CountryAppTheme {
        Surface {
            SortButton()
        }
    }
}


@Preview(name = "Dark mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun CountrySearchBarPreview() {
    CountryAppTheme {
        Surface {
            CountrySearchBar()
        }
    }
}


