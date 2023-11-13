package com.wenubey.countryapp.data.repository

import android.util.Log
import com.wenubey.countryapp.data.local.CountryCacheDao
import com.wenubey.countryapp.data.local.CountryUserFavouriteDao
import com.wenubey.countryapp.data.remote.CountryHistoryApi
import com.wenubey.countryapp.data.remote.CountryInfoApi
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.domain.repository.CountryRepository
import com.wenubey.countryapp.utils.Constants.TAG

class CountryRepositoryImpl(
    private val countryInfoApi: CountryInfoApi,
    private val countryHistoryApi: CountryHistoryApi,
    private val countryCacheDao: CountryCacheDao,
    private val countryUserFavouriteDao: CountryUserFavouriteDao,
) : CountryRepository {


    override suspend fun getAllCountries(fetchFromRemote: Boolean): Result<List<Country>> {
        val localCountryData = countryCacheDao.getAllCountriesFromCache()
        val isDbEmpty = localCountryData.isEmpty()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        if (shouldJustLoadFromCache) {
            // Return data from cache
            return Result.success(
                localCountryData.map { it.mapToCountry() }
            )
        }
        val remoteCountryData = try {
            countryInfoApi.getAllCountries()
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the error, e.g., log and return an empty list or throw an exception
            Log.e(TAG, "getAllCountries: error: $e")
            return Result.failure(e)
        }
        // Update the cache
        countryCacheDao.clearALl()
        countryCacheDao.upsertAll(remoteCountryData.map { it.mapToCountryEntity(null) })
        Log.i(
            TAG,
            "getAllCountries commonName: ${remoteCountryData.first().countryNameDto?.common}"
        )
        // Return data from the remote source
        return Result.success(
            remoteCountryData.map { it.mapToCountryEntity(null) }.map { it.mapToCountry() }
        )
    }

    override suspend fun getCountry(
        fetchFromRemote: Boolean,
        countryName: String
    ): Result<Country> {
        val localCountryData = countryCacheDao.getCountry(countryName)
        val historyIsNull = localCountryData?.historyEntity == null
        val countryIsNull = localCountryData == null
        return if (countryIsNull) {
            // return both data got remote and updated cache
            getCountryAndHistoryFromRemoteUpdateCountry(countryName)
        } else {
            if (historyIsNull) {
                // return cached country data but history data got from remote
                getHistoryFromRemoteUpdateCountry(countryName)
            } else {
                // return data from cache
                Result.success(localCountryData!!.mapToCountry())
            }
        }
    }

    private suspend fun getCountryAndHistoryFromRemoteUpdateCountry(countryName: String): Result<Country> {
        val remoteCountryData = try {
            countryInfoApi.getCountry(countryName)
        } catch (e: Exception) {
            Log.e(TAG, "remoteCountryData error: $e")
            return Result.failure(e)
        }
        val remoteHistoryData = try {
            countryHistoryApi.getHistoricalEvents(countryName)
        } catch (e: Exception) {
            Log.e(TAG, "getHistoricalEvents error: $e")
            return Result.failure(e)
        }
        // Data mapped to cache entity
        val localCountryData = remoteCountryData.mapToCountryEntity(
            historyDto = remoteHistoryData
        )
        // Cache Updated
        countryCacheDao.clearCountry(countryName)
        countryCacheDao.upsertCountry(localCountryData)
        return Result.success(localCountryData.mapToCountry())
    }


    private suspend fun getHistoryFromRemoteUpdateCountry(
        countryName: String,
    ): Result<Country> {
        val historyData = try {
            countryHistoryApi.getHistoricalEvents(countryName)
        } catch (e: Exception) {
            return Result.failure(e)
        }.map { it.mapToHistoryEntity() }
        val countryCacheEntity = countryCacheDao.getCountry(countryName)!!.copy(
            historyEntity = historyData
        )
        // Cache Updated
        countryCacheDao.clearCountry(countryName)
        countryCacheDao.upsertCountry(countryCacheEntity)

        val countryData = countryCacheEntity.mapToCountry()
        return Result.success(countryData)

    }

    override suspend fun getSearchedCountries(
        fetchFromRemote: Boolean,
        countryName: String
    ): Result<List<Country>> {
        val localCountryData = countryCacheDao.getAllCountriesFromCache()
        val isDbEmpty = localCountryData.isEmpty()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        if (shouldJustLoadFromCache) {
            // Return data from cache
            return Result.success(
                localCountryData
                    .map { it.mapToCountry() }
                    .filter {
                        it.countryCommonName!!.contains(countryName, true)
                    }
            )
        }
        val remoteCountryData = try {
            countryInfoApi.getAllCountries()
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the error, e.g., log and return an empty list or throw an exception
            Log.e(TAG, "getAllCountries: error: $e")
            return Result.failure(e)
        }
        // Update the cache
        countryCacheDao.clearALl()
        countryCacheDao.upsertAll(remoteCountryData.map { it.mapToCountryEntity(null) })
        Log.i(
            TAG,
            "getAllCountries commonName: ${remoteCountryData.first().countryNameDto?.common}"
        )
        // Return data from the remote source
        return Result.success(
            remoteCountryData
                .map { it.mapToCountryEntity(null) }
                .map { it.mapToCountry() }
                .filter { it.countryCommonName!!.contains(countryName,true) }
        )
    }
}