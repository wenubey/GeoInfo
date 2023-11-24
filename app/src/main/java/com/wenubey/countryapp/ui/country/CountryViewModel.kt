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

    var selectedSortOption = mutableStateOf(SortOption.NAME)
        private set
    var selectedSortOrder = mutableStateOf(SortOrder.ASC)
        private set

    private var searchJob: Job? = null
    private var countrySortJob: Job? = null
    private var combinedJob: Job? = null
    init {
        getAllCountries(
            fetchFromRemote = true
        )
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
                getCountry(false, event.query)
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
        countryName: String
    ) {
        viewModelScope.launch {
            val result = repo.getCountry(fetchFromRemote, countryName)
            countryDataState = processCountryResult(result)
        }
    }

    private fun processCountryResult(result: Result<Country>): CountryDataState {
        return if (result.isSuccess) {
            CountryDataState(
                country = result.getOrNull()
            )
        } else {
            CountryDataState(
                error = result.exceptionOrNull()?.message
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


}