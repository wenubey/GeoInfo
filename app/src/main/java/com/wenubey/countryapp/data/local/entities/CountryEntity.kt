package com.wenubey.countryapp.data.local.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity
data class CountryEntity(
    val countryNameEntity: CountryNameEntity?,
    val capital: String?,
    val population: Double?,
    @SerializedName("tld")
    val topLevelDomain: String?,
    @SerializedName("cca2")
    val countryCodeCCA2: String?,
    @SerializedName("independent")
    val isIndependent: Boolean?,
    @SerializedName("unMember")
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


data class CountryNameEntity(
    val common: String?,
    val official: String?,
    val nativeName: NativeNameEntity?
)

data class NativeNameEntity(
    val common: String?,
    val official: String?,
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