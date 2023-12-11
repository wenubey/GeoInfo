package com.wenubey.countryapp.data.repository

import com.wenubey.countryapp.data.local.CountryFavouriteDao
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.domain.repository.CountryFavRepository
import com.wenubey.countryapp.utils.Constants

class CountryFavRepositoryImpl(
    private val countryFavDao: CountryFavouriteDao,
) : CountryFavRepository {
    override suspend fun addCountryToFavourite(country: Country): Result<String> {
        return try {
            countryFavDao.upsertFavCountry(country.mapToCountryFavEntity())
            Result.success(Constants.FAV_SUCCESS_MESSAGE)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteCountryFromFavourite(country: Country): Result<String> {
        return try {
            countryFavDao.deleteFavCountry(country.mapToCountryFavEntity())
            Result.success(Constants.FAV_DELETE_MESSAGE)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearAllCountriesFromFavourite(): Result<String> {
        return try {
            countryFavDao.clearAllFavourites()
            Result.success(Constants.FAV_ALL_DELETE_MESSAGE)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllFavouriteCountries(): Result<List<Country>> {
        return try {
           val result = countryFavDao.getAllFavCountries().map { it.mapToCountry() }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}