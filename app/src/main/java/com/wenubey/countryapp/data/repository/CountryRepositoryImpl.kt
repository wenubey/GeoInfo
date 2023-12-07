package com.wenubey.countryapp.data.repository

import android.util.Log
import com.wenubey.countryapp.data.local.CountryCacheDao
import com.wenubey.countryapp.data.local.CountryUserFavouriteDao
import com.wenubey.countryapp.data.remote.CountryHistoryApi
import com.wenubey.countryapp.data.remote.CountryInfoApi
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.domain.repository.CountryRepository
import com.wenubey.countryapp.utils.Constants.TAG
import com.wenubey.countryapp.utils.CountryListOptions
import com.wenubey.countryapp.utils.Utils.Companion.printLog
import com.wenubey.countryapp.utils.normalizeCountryName

class CountryRepositoryImpl(
    private val countryInfoApi: CountryInfoApi,
    private val countryHistoryApi: CountryHistoryApi,
    private val countryCacheDao: CountryCacheDao,
    private val countryUserFavouriteDao: CountryUserFavouriteDao,
) : CountryRepository {


    override suspend fun getAllCountries(
        fetchFromRemote: Boolean,
        options: CountryListOptions
    ): Result<List<Country>> {
        val localCountryData = getSortedFilteredCountries(options)
        val isDbEmpty = localCountryData.isEmpty()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        if (shouldJustLoadFromCache) {
            // Return data from cache
            return Result.success(
                localCountryData
            )
        }
        val remoteCountryData = try {
            countryInfoApi.getAllCountries()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "getAllCountries: error: $e")
            return Result.failure(e)
        }
        // Update the cache
        countryCacheDao.clearALl()
        countryCacheDao.upsertAll(remoteCountryData.map { it.mapToCountryEntity(null) })
        // Return data from the remote source
        return Result.success(
            getSortedFilteredCountries(options)
        )
    }

    private suspend fun getSortedFilteredCountries(options: CountryListOptions = CountryListOptions.Default): List<Country> {
        return when (options) {
            is CountryListOptions.Filter -> {
                countryCacheDao.getSortedFilteredCountries(
                    query = options.query,
                    sortOption = null,
                    sortOrder = null
                ).map { it.mapToCountry() }
            }

            is CountryListOptions.Sort -> {
                countryCacheDao.getSortedFilteredCountries(
                    query = null,
                    sortOption = options.sortOption.name,
                    sortOrder = options.sortOrder.name
                ).map { it.mapToCountry() }
            }

            is CountryListOptions.Combined -> {
                countryCacheDao.getSortedFilteredCountries(
                    query = options.query,
                    sortOption = options.sortOption.name,
                    sortOrder = options.sortOrder.name
                ).map { it.mapToCountry() }
            }

            is CountryListOptions.Default -> countryCacheDao.getAllCountriesFromCache()
                .map { it.mapToCountry() }
        }
    }


    override suspend fun getCountry(
        fetchFromRemote: Boolean,
        countryName: String,
        countryCode: String,
    ): Result<Country> {
        val localCountryData = countryCacheDao.getCountry(countryCode)
        val historyIsNullOrEmpty =
            localCountryData?.historyEntity == null || localCountryData.historyEntity.isEmpty()
        val countryIsNull = localCountryData == null
        return if (countryIsNull) {
            // return both data got remote and updated cache
            getCountryAndHistoryFromRemoteUpdateCountry(
                countryName = countryName,
                countryCode = countryCode
            )
        } else {
            if (historyIsNullOrEmpty) {
                // return cached country data but history data got from remote
                getHistoryFromRemoteUpdateCountry(
                    countryName = countryName,
                    countryCode = countryCode
                )
            } else {
                // return data from cache
                Result.success(localCountryData!!.mapToCountry())
            }
        }
    }

    private suspend fun getCountryAndHistoryFromRemoteUpdateCountry(
        countryCode: String,
        countryName: String
    ): Result<Country> {
        val remoteCountryData = try {
            countryInfoApi.getCountry(countryCode)
        } catch (e: Exception) {
            Log.e(TAG, "remoteCountryData error: $e")
            return Result.failure(e)
        }
        val remoteHistoryData = try {
            countryHistoryApi.getHistoricalEvents(countryName)
        } catch (e: Exception) {
            Log.e(TAG, "getHistoricalEvents error: $e")
            return Result.failure(e)
        }.sortedBy { it.year?.toInt() }
        Log.i(TAG, "sorted histories: $remoteHistoryData")
        // Data mapped to cache entity
        val localCountryData = remoteCountryData.mapToCountryEntity(
            historyDto = remoteHistoryData
        )
        // Cache Updated
        countryCacheDao.clearCountry(countryCode)
        countryCacheDao.upsertCountry(localCountryData)

        return Result.success(countryCacheDao.getCountry(countryCode)!!.mapToCountry())
    }


    private suspend fun getHistoryFromRemoteUpdateCountry(
        countryCode: String,
        countryName: String,
    ): Result<Country> {
        val historyData = try {
            countryHistoryApi.getHistoricalEvents(normalizeCountryName(countryName)!!)
        } catch (e: Exception) {
            Log.e(TAG, "getHistoryFromRemote error: $e")
            return Result.failure(e)
        }
            .sortedBy { it.year?.toInt() }
            .map { it.mapToHistoryEntity() }


        val countryCacheEntity =
            countryCacheDao.getCountry(countryCode)?.copy(historyEntity = historyData)

        if (countryCacheEntity != null) {
            // Cache Updated
            countryCacheDao.clearCountry(countryCode)
            countryCacheDao.upsertCountry(countryCacheEntity)
        }

        val countryData = countryCacheDao.getCountry(countryCode)!!.mapToCountry()
        return Result.success(countryData)

    }

    override suspend fun getCountryCodeFromCache(): Result<Map<String?, String?>> {
        val localCountryData = countryCacheDao.getAllCountriesFromCache()
        val isDbEmpty = localCountryData.isEmpty()
        val shouldJustLoadFromCache = !isDbEmpty
        if (shouldJustLoadFromCache) {
            val flagEmojisWithPhoneCodes = localCountryData.map { it.flagEmojiWithPhoneCode }
            val countryCodesFromCache =
                flagEmojisWithPhoneCodes
                    .flatMap { it.entries }
                    .sortedBy { it.key }
                    .associate { it.toPair() }
            return Result.success(countryCodesFromCache)
        }
        val remoteCountryData = try {
            countryInfoApi.getAllCountries()
        } catch (e: Exception) {
            printLog(e)
            return Result.failure(e)
        }
        val newCountryCacheData = remoteCountryData.map { it.mapToCountryEntity(null) }
        countryCacheDao.clearALl()
        countryCacheDao.upsertAll(newCountryCacheData)

        val countryCodeFromRemote = newCountryCacheData.map { it.flagEmojiWithPhoneCode }
            .flatMap { it.entries }
            .sortedBy { it.key }
            .associate { it.toPair() }
        return Result.success(countryCodeFromRemote)
    }

    override suspend fun getLanguages(): Result<Map<String, String>> {
        val localLanguages = countryCacheDao.getLanguages()
        val distinctLangNames = localLanguages
            .mapNotNull { it?.data }
            .flatMap { it.entries }
            .sortedBy { it.key }
            .distinctBy { it.key }
            .associate { it.key to it.value }

        return Result.success(distinctLangNames)
    }
}