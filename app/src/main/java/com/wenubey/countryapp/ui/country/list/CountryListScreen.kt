package com.wenubey.countryapp.ui.country.list

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants


//TODO: Create country list screen
@Composable
fun CountryListScreen() {
    CountryListContent()
}

@Composable
private fun CountryListContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = Constants.COUNTRY_LIST_SCREEN_TITLE)
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

//    val isSearchBarActive = remember {
//        mutableStateOf(false)
//    }


//CountrySearchBar(
//query = countryViewModel.searchQuery.value,
//onQueryChange = {
//    countryViewModel.onEvent(
//        CountryEvent.OnSearchQueryChange(it)
//    )
//},
//active = isSearchBarActive.value,
//onActiveChange = { isSearchBarActive.value = it },
//countries = countries,
//onSortButtonClicked = { sortOption, sortOrder, query ->
//    onSortButtonClicked(
//        sortOption,
//        sortOrder,
//        query,
//        countryViewModel
//    )
//},
//onCardClick = navigateToCountryDetailScreen
//)


//private fun onSortButtonClicked(
//    sortOption: SortOption,
//    sortOrder: SortOrder,
//    query: String?,
//    viewModel: CountryViewModel
//) {
//    if (!query.isNullOrBlank()) {
//        viewModel.onEvent(
//            CountryEvent.OnGetAllCountriesFilteredAndSorted(
//                query = query,
//                sortOrder = sortOrder,
//                sortOption = sortOption
//            )
//        )
//    } else {
//        viewModel.onEvent(
//            CountryEvent.OnSortButtonClick(
//                sortOption = sortOption,
//                sortOrder = sortOrder
//            )
//        )
//    }
//}
