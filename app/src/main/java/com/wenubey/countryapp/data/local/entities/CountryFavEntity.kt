package com.wenubey.countryapp.data.local.entities

import androidx.room.Entity
import com.wenubey.countryapp.utils.Constants.FAV_TABLE_NAME

@Entity(tableName = FAV_TABLE_NAME)
data class CountryFavEntity(
    val countryCommonName: String?,
    val countryOfficialName: String?,
    val countryNativeCommonName: String?,
    val countryNativeOfficialName: String?,
    val capital: String?,
    val population: Double?,
    val topLevelDomain: String?,
    val countryCodeCCA2: String?,
    val isIndependent: Boolean?,
    val isUnMember: Boolean?,
    val currencyEntity: CurrencyEntity?,
    val region: String?,
    val subRegion: String?,
    val languageEntity: LanguageEntity?,
    val latlng: List<Double>?,
    val area: Double?,
    val timezone: String?,
    val historyEntity: HistoryEntity?
)
