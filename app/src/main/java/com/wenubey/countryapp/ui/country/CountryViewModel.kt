package com.wenubey.countryapp.ui.country

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenubey.countryapp.domain.repository.CountryRepository
import com.wenubey.countryapp.ui.country.detail.CountryDataState
import com.wenubey.countryapp.ui.country.list.CountryListDataState
import com.wenubey.countryapp.ui.country.list.CountryListEvent
import com.wenubey.countryapp.utils.Constants.UNKNOWN_ERROR
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

    private var searchJob: Job? = null

    init {
        getAllCountries()
    }

    fun onEvent(event: CountryListEvent) {
        when (event) {
            is CountryListEvent.Refresh -> {
                getAllCountries(true)
            }

            is CountryListEvent.OnSearchQueryChange -> {
                searchQuery.value = event.query
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    // Delay for user typing time
                    delay(500L)
                    val result =
                        repo.getSearchedCountries(fetchFromRemote = false, countryName = event.query)
                    countryListDataState =
                        if (result.isSuccess) {
                            countryListDataState.copy(
                                countries = result.getOrNull()
                            )
                        } else if (result.isFailure) {
                            countryListDataState.copy(
                                error = result.exceptionOrNull()?.message
                            )
                        } else {
                            countryListDataState.copy(
                                error = UNKNOWN_ERROR
                            )
                        }
                }
            }
        }
    }

    private fun getAllCountries(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            val result = repo.getAllCountries(fetchFromRemote)
            countryListDataState = if (result.isSuccess) {
                countryListDataState.copy(
                    countries = result.getOrNull()
                )
            } else if (result.isFailure) {
                countryListDataState.copy(
                    error = result.exceptionOrNull()?.message
                )
            } else {
                countryListDataState.copy(
                    error = UNKNOWN_ERROR
                )
            }
        }
    }


    fun getCountry(
        fetchFromRemote: Boolean = false,
        countryName: String
    ) {
        viewModelScope.launch {
            val result = repo.getCountry(fetchFromRemote, countryName)
            countryDataState = if (result.isSuccess) {
                countryDataState.copy(
                    country = result.getOrNull()
                )
            } else {
                countryDataState.copy(
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

}