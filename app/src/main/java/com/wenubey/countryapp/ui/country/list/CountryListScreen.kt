package com.wenubey.countryapp.ui.country.list

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.country.search_bar.CountrySearchBar
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants.TAG
import com.wenubey.countryapp.utils.SortOption
import com.wenubey.countryapp.utils.SortOrder
import com.wenubey.countryapp.utils.Utils.Companion.makeToast
import com.wenubey.countryapp.utils.components.ProgressBar
import com.wenubey.countryapp.utils.fakeCountry
import org.koin.androidx.compose.koinViewModel


//TODO: Create country list screen
@Composable
fun CountryListScreen(
    navigateToCountryDetailScreen: (countryCode: String?, countryName: String?) -> Unit,
    countryViewModel: CountryViewModel = koinViewModel(),
) {
    val context = LocalContext.current
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

    CountrySearchBar(
        query = countryViewModel.searchQuery.value,
        onQueryChange = {
            countryViewModel.onEvent(
                CountryEvent.OnSearchQueryChange(it)
            )
        },
        countries = countries,
        onSortButtonClicked = { sortOption, sortOrder, query, isFavorite ->
            onSortButtonClicked(
                sortOption,
                sortOrder,
                query,
                isFavorite,
                countryViewModel
            )
        },
        onCardClick = navigateToCountryDetailScreen,
        onFavoriteButtonClicked = {
            countryViewModel.onEvent(CountryEvent.OnFavoriteClicked(isFavorite = it))
        },
        isFavorite = countryViewModel.isFavoriteClicked.intValue
    )
    Log.i(
        TAG,
        "countryViewModel.isFavoriteClicked.intValue: ${countryViewModel.isFavoriteClicked.intValue}"
    )

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


@Preview(
    name = "Light Mode",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun CountryListContentPreview() {
    CountryAppTheme {
        Surface {
            CountryListContent()
        }
    }
}


