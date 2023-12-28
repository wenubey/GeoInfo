package com.wenubey.countryapp.ui.country.list

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.country.CountryEvent
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.SortOption
import com.wenubey.countryapp.utils.SortOrder
import com.wenubey.countryapp.utils.Utils.Companion.makeToast
import com.wenubey.countryapp.utils.components.ProgressBar
import com.wenubey.countryapp.utils.fakeCountry
import com.wenubey.countryapp.utils.toIcon
import org.koin.androidx.compose.koinViewModel


@Composable
fun CountryListScreen(
    navigateToCountryDetailScreen: (countryCode: String?, countryName: String?) -> Unit,
    countryViewModel: CountryViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val uiState = countryViewModel.countryListDataState
    LaunchedEffect(uiState) {
        countryViewModel.onEvent(CountryEvent.Refresh)
    }
    if (uiState.isLoading) {
        ProgressBar()
    } else if (uiState.countries != null) {
        val countries = uiState.countries
        CountryListContent(
            countries = countries,
            navigateToCountryDetailScreen = navigateToCountryDetailScreen
        )
    } else if (uiState.error != null) {
        //TODO Make error screen
        context.makeToast("Error: ${uiState.error}")
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
        onSortButtonClicked = { sortOption, sortOrder, query, isFavorite ->
            onSortButtonClicked(sortOption, sortOrder, query, isFavorite, countryViewModel)
        },
        onCardClick = navigateToCountryDetailScreen,
        onFavoriteFilterButtonClicked = {
            countryViewModel.onEvent(CountryEvent.OnFavoriteClicked(isFavorite = it))
        },
        isFavorite = countryViewModel.isFavoriteFilterClicked.intValue,
        onFavButtonClicked = { country, countryUpdatedFav ->
            countryViewModel.onEvent(
                CountryEvent.OnUserUpdateFavorite(country, countryUpdatedFav)
            )
        }
    )

}

@Composable
private fun CountryList(
    query: String,
    onQueryChange: (String) -> Unit,
    countries: List<Country>,
    onSortButtonClicked: (sortOption: SortOption, sortOrder: SortOrder, query: String?, isFavorite: Int) -> Unit,
    onCardClick: (countryCode: String?, countryName: String?) -> Unit,
    onFavoriteFilterButtonClicked: (Int) -> Unit,
    isFavorite: Int,
    onFavButtonClicked: (country: Country, countryUpdatedFav: Boolean) -> Unit,
) {
    CountryListContent(
        query = query,
        onQueryChange = onQueryChange,
        countries = countries,
        onSortButtonClicked = onSortButtonClicked,
        onCardClick = onCardClick,
        onFavoriteFilterButtonClicked = onFavoriteFilterButtonClicked,
        isFavorite = isFavorite,
        onFavButtonClicked = onFavButtonClicked,
    )
}


@Composable
private fun CountryListContent(
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    countries: List<Country> = listOf(fakeCountry),
    onSortButtonClicked: (sortOption: SortOption, sortOrder: SortOrder, query: String?, isFavorite: Int) -> Unit = { _, _, _, _ -> },
    onCardClick: (countryCode: String?, countryName: String?) -> Unit = { _, _ -> },
    isFavorite: Int = 0,
    onFavoriteFilterButtonClicked: (Int) -> Unit = {},
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
            onFavoriteFilterButtonClicked = onFavoriteFilterButtonClicked,
            isFavorite = isFavorite
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
                text = Constants.MAP_SEARCH_BAR_PLACEHOLDER,
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
    isFavorite: Int,
    onSortButtonClicked: (sortOption: SortOption, sortOrder: SortOrder, query: String, isFavorite: Int) -> Unit,
    onFavoriteFilterButtonClicked: (Int) -> Unit
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
                } else {
                    0
                }
                onFavoriteFilterButtonClicked(newQuery)
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


private fun onSortButtonClicked(
    sortOption: SortOption,
    sortOrder: SortOrder,
    query: String?,
    isFavorite: Int,
    viewModel: CountryViewModel
) {
    if (!query.isNullOrBlank()) {
        viewModel.onEvent(
            CountryEvent.OnGetAllCountriesFilteredAndSorted(
                query = query,
                sortOrder = sortOrder,
                sortOption = sortOption,
                isFavorite = isFavorite
            )
        )
    } else {
        viewModel.onEvent(
            CountryEvent.OnSortButtonClick(
                sortOption = sortOption,
                sortOrder = sortOrder,
                isFavorite = isFavorite
            )
        )
    }
}


//@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
//@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//private fun CountryListContentPreview() {
//    CountryAppTheme {
//        Surface {
//            CountryListContent()
//        }
//    }
//}

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


