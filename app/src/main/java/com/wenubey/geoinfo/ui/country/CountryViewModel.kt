package com.wenubey.geoinfo.ui.country

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.wenubey.geoinfo.domain.model.Country
import com.wenubey.geoinfo.domain.repository.CountryRepository
import com.wenubey.geoinfo.ui.country.detail.CountryDataState
import com.wenubey.geoinfo.ui.country.list.CountryListDataState
import com.wenubey.geoinfo.utils.CountryListOptions
import com.wenubey.geoinfo.utils.DataResponse
import com.wenubey.geoinfo.utils.SortOption
import com.wenubey.geoinfo.utils.SortOrder
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountryViewModel(
    private val repo: CountryRepository,
) : ViewModel() {

    var countryDataState by mutableStateOf(CountryDataState())
        private set

    var countryListDataState by mutableStateOf(CountryListDataState())
        private set

    var favCountriesLatLng by mutableStateOf<List<LatLng>>(emptyList())
        private set

    var searchQuery = mutableStateOf("")
        private set

    private var selectedSortOption = mutableStateOf(SortOption.NAME)

    private var selectedSortOrder = mutableStateOf(SortOrder.ASC)

    var countryPhoneCodes by mutableStateOf<Map<String?, String?>>(emptyMap())
        private set

    var countryLanguageNames by mutableStateOf<Map<String, String>>(emptyMap())
        private set


    private var searchJob: Job? = null
    private var countrySortJob: Job? = null
    private var combinedJob: Job? = null

    init {
        getAllCountries()
        getCountryCodes()
        getCountryLanguages()
    }

    fun onEvent(event: CountryEvent) {
        when (event) {
            is CountryEvent.OnSearchQueryChange -> {
                Log.i(TAG, "CountryEvent.OnSearchQueryChange: searchQuery.value: ${event.query}\n searchQuery: ${event.query}")
                searchQuery.value = event.query
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    // Delay for user typing time
                    delay(500L)
                    val result =
                        repo.getAllCountries(
                            fetchFromRemote = false,
                            options = CountryListOptions.Filter(
                                query = event.query,
                            )
                        )
                    countryListDataState = processCountriesResult(result)
                }
            }

            is CountryEvent.OnSortButtonClick -> {
                Log.i(TAG, "CountryEvent.OnSortButtonClick:  selectedSortOption.value: ${event.sortOption}, selectedSortOrder.value: ${event.sortOrder}")
                selectedSortOption.value = event.sortOption
                countrySortJob?.cancel()
                countrySortJob = viewModelScope.launch {
                    val result = repo.getAllCountries(
                        options = CountryListOptions.Sort(
                            sortOption = event.sortOption,
                            sortOrder = event.sortOrder,
                        ),
                        fetchFromRemote = false
                    )
                    countryListDataState = processCountriesResult(result)
                }
            }

            is CountryEvent.OnGetAllCountriesFilteredAndSorted -> {
                Log.i(TAG, "CountryEvent.OnGetAllCountriesFilteredAndSorted:  selectedSortOption.value: ${event.sortOption}\n selectedSortOption.value: ${event.sortOption}\n selectedSortOrder.value: ${event.sortOrder}")
                if (event.query.isNotBlank()) {
                    searchQuery.value = event.query
                    selectedSortOption.value = event.sortOption
                    selectedSortOrder.value = event.sortOrder
                    combinedJob?.cancel()
                    combinedJob = viewModelScope.launch {
                        val result = repo.getAllCountries(
                            fetchFromRemote = false,
                            options = CountryListOptions.Combined(
                                sortOption = event.sortOption,
                                sortOrder = event.sortOrder,
                                query = event.query,
                            )
                        )
                        countryListDataState = processCountriesResult(result)
                    }
                }
            }

            is CountryEvent.OnGetCountry -> {
                getCountry(event.countryName, event.countryCode)
            }
            is CountryEvent.OnUserUpdateFavorite -> {
                updateFavCountry(event.country, event.isFavorite)
            }
            is CountryEvent.OnGetAllFavoriteCountries -> {
                getAllFavCountries()
            }
        }
    }

   private fun getAllFavCountries() {
        viewModelScope.launch {
            favCountriesLatLng = repo.getLatLngFavCountries()
        }
    }

    private fun getAllCountries(
        fetchFromRemote: Boolean = false,
        options: CountryListOptions = CountryListOptions.Default
    ) {
        viewModelScope.launch {
            val result = repo.getAllCountries(fetchFromRemote = fetchFromRemote, options = options)
            countryListDataState = processCountriesResult(result)
        }
    }

    private fun getCountry(
        countryName: String,
        countryCode: String
    ) {
        viewModelScope.launch {
            val result = repo.getCountry(countryName = countryName, countryCode = countryCode)
            countryDataState = processCountryResult(result)
        }
    }

    private fun processCountryResult(result: DataResponse<Country>): CountryDataState {
        return when (result) {
            is DataResponse.Success -> {
                CountryDataState(country = result.data)
            }

            is DataResponse.Error -> {
                CountryDataState(error = result.error.message)
            }

            is DataResponse.Loading -> {
                CountryDataState(isLoading = result.isLoading)
            }
        }
    }

    private fun processCountriesResult(result: DataResponse<List<Country>>): CountryListDataState {
        return when (result) {
            is DataResponse.Success -> {
                countryListDataState.copy(countries = result.data)
            }

            is DataResponse.Error -> {
                countryListDataState.copy(error = result.error.message)
            }

            is DataResponse.Loading -> {
                countryListDataState.copy(isLoading = result.isLoading)
            }
        }
    }

    private fun getCountryCodes() = viewModelScope.launch {

        val countryPhoneCodesResult = repo.getCountryCodeFromCache()
        countryPhoneCodes = if (countryPhoneCodesResult.isSuccess) {
            countryPhoneCodesResult.getOrNull()!!
        } else {
            emptyMap()
        }

    }

    private fun getCountryLanguages() = viewModelScope.launch {
        val countryLanguagesResult = repo.getLanguages()
        countryLanguageNames = if (countryLanguagesResult.isSuccess) {
            countryLanguagesResult.getOrNull()!!
        } else {
            emptyMap()
        }
    }

    private fun updateFavCountry(country: Country, isFavorite: Boolean) = viewModelScope.launch {
        repo.updateFavCountry(country, isFavorite)
    }

    companion object {
        private const val TAG = "CountryViewModel"
    }
}