package com.wenubey.countryapp.data.remote

import com.google.gson.annotations.SerializedName
import com.wenubey.countryapp.data.local.entities.CountryCacheEntity
import com.wenubey.countryapp.data.local.entities.CurrencyEntity
import com.wenubey.countryapp.data.local.entities.FlagEntity
import com.wenubey.countryapp.data.local.entities.HistoryEntity
import com.wenubey.countryapp.data.local.entities.LanguageEntity
import com.wenubey.countryapp.data.local.entities.NativeNameEntity
import com.wenubey.countryapp.utils.parseDate

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
    val historyDto: List<HistoryDto>?
) {
    fun mapToCountryEntity(historyDto: List<HistoryDto>?): CountryCacheEntity {
        return CountryCacheEntity(
            countryCommonName = countryNameDto?.common,
            countryOfficialName = countryNameDto?.official,
            countryNativeCommonName = countryNameDto?.nativeName?.common,
            countryNativeOfficialName = countryNameDto?.nativeName?.official,
            capital = capital,
            population = population,
            topLevelDomain = topLevelDomain,
            countryCodeCCA2 = countryCodeCCA2,
            isIndependent = isIndependent,
            isUnMember = isUnMember,
            currencyEntity = currencyDto?.mapToCurrencyEntity(),
            region = region,
            subRegion = subRegion,
            languageEntity = languageDto?.mapToLanguageEntity(),
            latlng = latlng,
            area = area,
            flagEntity = flagDto?.mapToFlagEntity(),
            timezone = timezone,
            coatOfArmsPng = coatOfArmsDto?.png,
            historyEntity = historyDto?.map { it.mapToHistoryEntity() },
        )
    }
}

data class CountryNameDto(
    val common: String?,
    val official: String?,
    val nativeName: NativeNameDto?
)

data class NativeNameDto(
    val common: String?,
    val official: String?,
) {
    fun mapToNativeNameEntity(): NativeNameEntity {
        return NativeNameEntity(
            common = common,
            official = official
        )
    }
}

data class CurrencyDto(
    val name: String?,
    val symbol: String?
) {
    fun mapToCurrencyEntity(): CurrencyEntity {
        return CurrencyEntity(
            name = name,
            symbol = symbol
        )
    }
}

data class LanguageDto(
    val name: String?
) {
    fun mapToLanguageEntity(): LanguageEntity {
        return LanguageEntity(
            name = name
        )
    }
}

data class FlagDto(
    val png: String?,
    val alt: String?
) {
    fun mapToFlagEntity(): FlagEntity {
        return FlagEntity(
            png = png,
            alt = alt
        )
    }
}

data class CoatOfArmsDto(
    val png: String?
)

data class HistoryDto(
    val year: String?,
    val month: String?,
    val day: String?,
    val event: String?
) {
    fun mapToHistoryEntity(): HistoryEntity {
        return HistoryEntity(
            date = parseDate(day = day, month = month, year = year),
            event = event
        )
    }
}