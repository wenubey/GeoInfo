package com.wenubey.countryapp.ui.map

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.country.list.CountryEvent
import com.wenubey.countryapp.ui.map.components.GoogleMaps
import com.wenubey.countryapp.ui.map.components.MapScreenTopBar
import com.wenubey.countryapp.ui.map.components.search_bar.CountrySearchBar
import com.wenubey.countryapp.ui.profile.ProfileViewModel
import com.wenubey.countryapp.utils.Constants.TAG
import com.wenubey.countryapp.utils.SortOption
import com.wenubey.countryapp.utils.SortOrder
import org.koin.androidx.compose.getViewModel

@Composable
fun MapScreen(
    navigateToProfileScreen: () -> Unit,
    navigateToCountryDetailScreen: (countryName: String?) -> Unit,
) {

    val countryViewModel: CountryViewModel = getViewModel()
    val profileViewModel: ProfileViewModel = getViewModel()
    val isSearchBarActive = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            MapScreenTopBar(
                navigateToProfileScreen = navigateToProfileScreen,
                photoUri = profileViewModel.currentUser?.photoUrl
            )
        },
        content = { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize()
            ) {
                countryViewModel.countryListDataState.countries?.let { countries ->
                    CountrySearchBar(
                        query = countryViewModel.searchQuery.value,
                        onQueryChange = {
                            countryViewModel.onEvent(
                                CountryEvent.OnSearchQueryChange(it)
                            )
                        },
                        active = isSearchBarActive.value,
                        onActiveChange = {isSearchBarActive.value = it},
                        countries = countries,
                        onSortButtonClicked = {sortOption, sortOrder, query -> onSortButtonClicked(sortOption, sortOrder, query, countryViewModel) },
                        onCardClick = navigateToCountryDetailScreen
                    )
                }
                GoogleMaps(onMapClick = { countryName ->
                    navigateToCountryDetailScreen(countryName)
                })
            }

        }
    )

}
private fun onSortButtonClicked(sortOption: SortOption, sortOrder: SortOrder, query: String?, viewModel: CountryViewModel) {
    if (!query.isNullOrBlank()) {
        Log.i(TAG, "sortOption: $sortOption, sortOrder: $sortOrder, query: $query")
        viewModel.onEvent(
            CountryEvent.OnGetAllCountriesFilteredAndSorted(
                query = query,
                sortOrder =sortOrder,
                sortOption = sortOption
            )
        )
    } else {
        Log.i(TAG, "sortOption: $sortOption, sortOrder: $sortOrder, query: $query")
        viewModel.onEvent(
            CountryEvent.OnSortButtonClick(
                sortOption = sortOption,
                sortOrder = sortOrder
            )
        )
    }

}
