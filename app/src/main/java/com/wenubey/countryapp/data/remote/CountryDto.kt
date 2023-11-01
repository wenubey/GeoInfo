package com.wenubey.countryapp.data.remote

import com.google.gson.annotations.SerializedName

data class CountryDto(
    val countryNameDto: CountryNameDto?,
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
    val currencyDto: CurrencyDto?,
    val region: String?,
    val subRegion: String?,
    val languageDto: LanguageDto?,
    val latlng: List<Double>?,
    val area: Double?,
    val flagDto: FlagDto?,
    val timezone: String?,
    val coatOfArmsDto: CoatOfArmsDto?,
    val historyDto: HistoryDto?
)

data class CountryNameDto(
    val common: String?,
    val official: String?,
    val nativeName: NativeNameDto?
)

data class NativeNameDto(
    val common: String?,
    val official: String?,
)

data class CurrencyDto(
    val name: String?,
    val symbol: String?
)

data class LanguageDto(
    val name: String?
)

data class FlagDto(
    val png: String?,
    val alt: String?
)
data class CoatOfArmsDto(
    val png: String?
)

data class HistoryDto(
    val year: String?,
    val month: String?,
    val day: String?,
    val event: String?
)