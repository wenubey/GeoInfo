package com.wenubey.countryapp.ui.country

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.domain.repository.CountryRepository
import com.wenubey.countryapp.ui.country.detail.CountryDataState
import com.wenubey.countryapp.ui.country.list.CountryListDataState
import com.wenubey.countryapp.ui.country.list.CountryEvent
import com.wenubey.countryapp.utils.Constants.UNKNOWN_ERROR
import com.wenubey.countryapp.utils.CountryListOptions
import com.wenubey.countryapp.utils.SortOption
import com.wenubey.countryapp.utils.SortOrder
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountryViewModel(
    private val repo: CountryRepository
) : ViewModel() {

    var countryDataState by mutableStateOf(CountryDataState())
        private set

    var countryListDataState by mutableStateOf(CountryListDataState())
        private set

    var searchQuery = mutableStateOf("")
        private set

    private var selectedSortOption = mutableStateOf(SortOption.NAME)

    private var selectedSortOrder = mutableStateOf(SortOrder.ASC)

    var countryPhoneCodes by mutableStateOf<Map<String?,String?>>(emptyMap())
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
            is CountryEvent.Refresh -> {
                getAllCountries(
                    fetchFromRemote = true,
                )
            }
            is CountryEvent.OnSearchQueryChange -> {
                searchQuery.value = event.query
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    // Delay for user typing time
                    delay(500L)
                    val result =
                        repo.getAllCountries(
                            fetchFromRemote = false,
                            options = CountryListOptions.Filter(
                                query = event.query
                            )
                        )
                    countryListDataState = processCountriesResult(result)
                }
            }
            is CountryEvent.OnGetAllCountries -> {
                getAllCountries(fetchFromRemote = true, event.options)
            }
            is CountryEvent.OnSortButtonClick -> {
                selectedSortOption.value = event.sortOption
                countrySortJob?.cancel()
                countrySortJob = viewModelScope.launch {
                    val result = repo.getAllCountries(
                        options = CountryListOptions.Sort(
                            sortOption = event.sortOption,
                            sortOrder = event.sortOrder
                        ),
                        fetchFromRemote = false
                    )
                    countryListDataState = processCountriesResult(result)
                }
            }
            is CountryEvent.OnGetAllCountriesFilteredAndSorted -> {
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
                getCountry(false, event.countryName, event.countryCode)
            }

        }
    }

    private fun getAllCountries(fetchFromRemote: Boolean = false, options: CountryListOptions = CountryListOptions.Default) {
        viewModelScope.launch {
            val result = repo.getAllCountries(fetchFromRemote = fetchFromRemote, options = options)
            countryListDataState = processCountriesResult(result)
        }
    }



   private fun getCountry(
        fetchFromRemote: Boolean = false,
        countryName: String,
        countryCode: String
    ) {
        viewModelScope.launch {
            val result = repo.getCountry(fetchFromRemote, countryName, countryCode)
            countryDataState = processCountryResult(result)
        }
    }

    private fun processCountryResult(result: Result<Country>): CountryDataState {
        return if (result.isSuccess) {
            CountryDataState(isLoading = true)
            CountryDataState(
                country = result.getOrNull(),
                isLoading = false
            )
        } else {
            CountryDataState(
                error = result.exceptionOrNull()?.message,
                isLoading = false,
            )
        }
    }

    private fun processCountriesResult(result: Result<List<Country>>): CountryListDataState {
        return if (result.isSuccess) {
            CountryListDataState(
                countries = result.getOrNull(),
            )
        } else if (result.isFailure) {
            CountryListDataState(
                error = result.exceptionOrNull()?.message,
            )
        } else {
            CountryListDataState(
                error = UNKNOWN_ERROR,
            )
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
}