package com.wenubey.countryapp.data.repository

import com.wenubey.countryapp.data.local.CountryCacheDao
import com.wenubey.countryapp.data.local.CountryUserFavouriteDao
import com.wenubey.countryapp.data.remote.CountryHistoryApi
import com.wenubey.countryapp.data.remote.CountryInfoApi
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.domain.repository.CountryRepository
import com.wenubey.countryapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountryRepositoryImpl(
    private val countryInfoApi: CountryInfoApi,
    private val countryHistoryApi: CountryHistoryApi,
    private val countryCacheDao: CountryCacheDao,
    private val countryUserFavouriteDao: CountryUserFavouriteDao,
) : CountryRepository {

    //TODO Erolla bir tartis
    override suspend fun getAllCountries(fetchFromRemote: Boolean): Flow<Resource<List<Country>>> {
        return flow {
            emit(Resource.Loading(true))
            val localCountryData = countryCacheDao.getAllCountriesFromCache()
            emit(Resource.Success(
                data = localCountryData.map { it.mapToCountry() }
            ))
            val isDbEmpty = localCountryData.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteCountryData = try {
                countryInfoApi.getAllCountries()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message))
                null
            }
            remoteCountryData?.let { data ->
                countryCacheDao.clearALl()
                countryCacheDao.upsertAll(data.map { it.mapToCountryEntity(null) })
                emit(Resource.Success(
                    data = countryCacheDao
                        .getAllCountriesFromCache()
                        .map { it.mapToCountry() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCountry(
        fetchFromRemote: Boolean,
        countryName: String
    ): Flow<Resource<Country>> {
        return flow {
            emit(Resource.Loading(true))
            val localCountryData = countryCacheDao.getCountry(countryName)
            val isHistoryDataNull = localCountryData.historyEntity == null
            if (isHistoryDataNull) {
                val remoteHistoryData = try {
                    countryHistoryApi.getHistoricalEvents(countryName)
                        .map { it.mapToHistoryEntity() }
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Resource.Error(e.message))
                    null
                }
                remoteHistoryData?.let { historyEntity ->
                    emit(Resource.Success(
                        data = localCountryData.copy(
                            historyEntity = historyEntity
                        ).mapToCountry()
                    ))
                }
                emit(Resource.Loading(false))
            } else {
                emit(Resource.Success(
                    data = localCountryData.mapToCountry()
                ))
                emit(Resource.Loading(false))
            }
        }
    }
}