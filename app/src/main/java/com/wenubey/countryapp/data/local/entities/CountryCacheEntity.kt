package com.wenubey.countryapp.data.local.entities

import androidx.room.Entity
import com.wenubey.countryapp.utils.Constants.CACHE_TABLE_NAME
import java.util.Date

@Entity(tableName = CACHE_TABLE_NAME)
data class CountryEntity(
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
    val flagEntity: FlagEntity?,
    val timezone: String?,
    val coatOfArmsEntity: CoatOfArmsEntity?,
    val historyEntity: HistoryEntity?
)



data class CurrencyEntity(
    val name: String?,
    val symbol: String?
)

data class LanguageEntity(
    val name: String?
)

data class FlagEntity(
    val png: String?,
    val alt: String?
)
data class CoatOfArmsEntity(
    val png: String?
)

data class HistoryEntity(
    val date: Date?,
    val event: String?
)