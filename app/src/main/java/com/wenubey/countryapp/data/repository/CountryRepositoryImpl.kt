package com.wenubey.countryapp.data.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wenubey.countryapp.data.local.CountryCacheDao
import com.wenubey.countryapp.data.remote.CountryHistoryApi
import com.wenubey.countryapp.data.remote.CountryInfoApi
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.domain.repository.CountryRepository
import com.wenubey.countryapp.utils.Constants
import com.wenubey.countryapp.utils.Constants.TAG
import com.wenubey.countryapp.utils.CountryListOptions
import com.wenubey.countryapp.utils.DataResponse
import com.wenubey.countryapp.utils.Utils.Companion.printLog
import com.wenubey.countryapp.utils.normalizeCountryName
import kotlinx.coroutines.tasks.await

class CountryRepositoryImpl(
    private val countryInfoApi: CountryInfoApi,
    private val countryHistoryApi: CountryHistoryApi,
    private val countryCacheDao: CountryCacheDao,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : CountryRepository {


    override suspend fun getAllCountries(
        fetchFromRemote: Boolean,
        options: CountryListOptions
    ): DataResponse<List<Country>> {
        val localCountryData = getSortedFilteredCountries(options)
        val isDbEmpty = localCountryData.isEmpty()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        DataResponse.Loading(isLoading = true)
        if (shouldJustLoadFromCache) {
            // Return data from cache
            return DataResponse.Success(
                localCountryData
            )
        }
        DataResponse.Loading(isLoading = true)
        val remoteCountryData = try {
            countryInfoApi.getAllCountries()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "getAllCountries: error: $e")
            return DataResponse.Error(e)
        }

        // Update the cache
        countryCacheDao.clearALl()
        countryCacheDao.upsertAll(remoteCountryData.map { it.mapToCountryEntity(null) }.distinct())

        // Return data from the remote source
        return DataResponse.Success(
            getSortedFilteredCountries(options)
        )
    }

    private suspend fun getSortedFilteredCountries(options: CountryListOptions = CountryListOptions.Default): List<Country> {
        return when (options) {
            is CountryListOptions.Filter -> {
                countryCacheDao.getSortedFilteredCountries(
                    query = options.query,
                    sortOption = null,
                    sortOrder = null,
                    isFavorite = null,
                ).map { it.mapToCountry() }
            }

            is CountryListOptions.Sort -> {
                countryCacheDao.getSortedFilteredCountries(
                    query = null,
                    sortOption = options.sortOption.name,
                    sortOrder = options.sortOrder.name,
                    isFavorite = null,
                ).map { it.mapToCountry() }
            }

            is CountryListOptions.Combined -> {
                countryCacheDao.getSortedFilteredCountries(
                    query = options.query,
                    sortOption = options.sortOption.name,
                    sortOrder = options.sortOrder.name,
                    isFavorite = options.isFavorite
                ).map { it.mapToCountry() }
            }

            is CountryListOptions.Favorite -> {
                countryCacheDao.getSortedFilteredCountries(
                    query = null,
                    sortOption = null,
                    sortOrder = null,
                    isFavorite = options.isFavorite
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
    ): DataResponse<Country> {
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
                DataResponse.Success(localCountryData!!.mapToCountry())
            }
        }
    }

    private suspend fun getCountryAndHistoryFromRemoteUpdateCountry(
        countryCode: String,
        countryName: String
    ): DataResponse<Country> {
        val remoteCountryData = try {
            countryInfoApi.getCountry(countryCode)
        } catch (e: Exception) {
            Log.e(TAG, "remoteCountryData error: $e")
            return DataResponse.Error(e)
        }
        val remoteHistoryData = try {
            countryHistoryApi.getHistoricalEvents(countryName)
        } catch (e: Exception) {
            Log.e(TAG, "getHistoricalEvents error: $e")
            return DataResponse.Error(e)
        }.sortedBy { it.year?.toInt() }
        Log.i(TAG, "sorted histories: $remoteHistoryData")
        // Data mapped to cache entity
        val localCountryData = remoteCountryData.mapToCountryEntity(
            historyDto = remoteHistoryData
        )
        // Cache Updated
        countryCacheDao.clearCountry(countryCode)
        countryCacheDao.upsertCountry(localCountryData)

        return DataResponse.Success(countryCacheDao.getCountry(countryCode)!!.mapToCountry())
    }


    private suspend fun getHistoryFromRemoteUpdateCountry(
        countryCode: String,
        countryName: String,
    ): DataResponse<Country> {
        val historyData = try {
            DataResponse.Loading(true)
            countryHistoryApi.getHistoricalEvents(normalizeCountryName(countryName)!!)
        } catch (e: Exception) {
            Log.e(TAG, "getHistoryFromRemote error: $e")
            return DataResponse.Error(e)
        }
            .sortedBy { it.year?.toInt() }
            .map { it.mapToHistoryEntity() }

        DataResponse.Loading(true)
        val countryCacheEntity =
            countryCacheDao.getCountry(countryCode)?.copy(historyEntity = historyData)

        if (countryCacheEntity != null) {
            // Cache Updated
            countryCacheDao.clearCountry(countryCode)
            countryCacheDao.upsertCountry(countryCacheEntity)
        }


        val countryData = countryCacheDao.getCountry(countryCode)?.mapToCountry()
        return if (countryData != null) {
            DataResponse.Success(countryData)
        } else {
            DataResponse.Loading(true)
        }
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

    override suspend fun updateFavCountry(country: Country, isFavorite: Boolean){
        val userId = auth.currentUser?.uid
        return if (userId != null) {
            val currentDocument = firestore.collection(Constants.USERS).document(userId)

            // Retrieve existing data and favCountries set
            val existingData = currentDocument.get().await().data
            val existingFavCountries = existingData?.get("favCountries") as? String ?: ""
            val favCountriesSet = existingFavCountries.split(",").toMutableSet()

            // Update favCountries set based on isFavorite status
            if (isFavorite) {
                favCountriesSet.add(country.countryCodeCCA2!!)
            } else {
                favCountriesSet.remove(country.countryCodeCCA2!!)
            }

            // Build the update map
            val updateValue = mapOf(
                "favCountries" to favCountriesSet.joinToString(",")
            )
            // Update the Firestore document
            currentDocument.update(updateValue).await()
            // Update the local database
            val existingValue = countryCacheDao.getCountry(country.countryCodeCCA2)
            val updatedValue = existingValue!!.copy(isFavorite = isFavorite)
            countryCacheDao.upsertCountry(updatedValue)
        } else {

        }
    }

    override suspend fun getLatLngFavCountries(): List<LatLng> {
        //TODO add fetch from firebase and sync with local and than exposed to the UI
        return countryCacheDao.getLatLngFavCountries()
    }
}


