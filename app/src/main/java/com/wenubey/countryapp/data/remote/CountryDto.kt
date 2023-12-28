package com.wenubey.countryapp.data.remote

import com.squareup.moshi.Json
import com.wenubey.countryapp.data.local.entities.CountryCacheEntity
import com.wenubey.countryapp.data.local.entities.CurrencyEntity
import com.wenubey.countryapp.data.local.entities.HistoryEntity
import com.wenubey.countryapp.data.local.entities.LanguageEntity
import com.wenubey.countryapp.data.local.entities.NativeNameEntity
import com.wenubey.countryapp.data.local.entities.TranslationEntity
import com.wenubey.countryapp.utils.getLatLngFromRemote
import com.wenubey.countryapp.utils.parseEventDate


data class CountryDto(
    @field:Json(name = "name")
    val countryNameDto: CountryNameDto?,
    val capital: List<String>?,
    val population: Int?,
    @field:Json(name = "tld")
    val topLevelDomain: List<String>?,
    @field:Json(name = "cca2")
    val countryCodeCCA2: String,
    @field:Json(name = "currencies")
    val currencyDto: Map<String, CurrencyDto>?,
    val region: String?,
    @field:Json(name = "subregion")
    val subRegion: String?,
    @field:Json(name = "languages")
    val languageDto: Map<String, String>?,
    val latlng: List<Double>?,
    val area: Double?,
    @field:Json(name = "flags")
    val flagDto: Map<String, String>?,
    val timezones: List<String>?,
    @field:Json(name = "coatOfArms")
    val coatOfArmsDto: Map<String, String>?,
    val historyDto: List<HistoryDto>?,
    @field:Json(name = "flag")
    val flagEmoji: String?,
    @field:Json(name = "idd")
    val iddDto: IddDto?,
    @field:Json(name = "gini")
    val gini: Map<String?, Double?>?,
    @field:Json(name = "demonyms")
    val demonyms: Map<String?, Map<String?, String?>?>?,
    @field:Json(name = "translations")
    val translations: Map<String?, TranslationDto?>?,
    @field:Json(name = "continents")
    val continents: List<String?>?,
    @field:Json(name = "borders")
    val borders: List<String?>?
) {
    fun mapToCountryEntity(historyDto: List<HistoryDto>?): CountryCacheEntity {
        return CountryCacheEntity(
            countryCommonName = countryNameDto?.common,
            countryOfficialName = countryNameDto?.official,
            countryNativeName = countryNameDto?.nativeName?.mapValues { it.value.mapToNativeNameEntity() },
            capital = capital,
            population = population,
            topLevelDomain = topLevelDomain,
            countryCodeCCA2 = countryCodeCCA2,
            currencyEntity = currencyDto?.mapValues { it.value.mapToCurrencyEntity() },
            region = region,
            subRegion = subRegion,
            languageEntity = LanguageEntity(data = languageDto),
            latlng = latlng.getLatLngFromRemote(),
            area = area,
            flagEntity = flagDto,
            timezones = timezones,
            coatOfArms = coatOfArmsDto,
            historyEntity = historyDto?.map { it.mapToHistoryEntity() },
            flagEmojiWithPhoneCode = mapOf(flagEmoji + " ${countryNameDto?.common}" to iddDto?.mapToPhoneCode()),
            flagEmoji = flagEmoji,
            gini = gini ?: mapOf(),
            demonyms = demonyms,
            translations = translations?.mapValues { it.value?.mapToTranslationEntity() } ?: mapOf(),
            continents = continents,
            borders = borders,
            isFavorite = false
            )
    }
}

data class CountryNameDto(
    val common: String?,
    val official: String?,
    val nativeName: Map<String, NativeNameDto>?
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


data class HistoryDto(
    val year: String?,
    val month: String?,
    val day: String?,
    val event: String?
) {
    fun mapToHistoryEntity(): HistoryEntity {
        return HistoryEntity(
            date = parseEventDate(day = day, month = month, year = year),
            event = event
        )
    }
}

data class IddDto(
    val root: String?,
    val suffixes: List<String>?
) {
    fun mapToPhoneCode(): String {
        return root + suffixes?.first()
    }
}

data class TranslationDto(
    val official: String?,
    val common: String?
) {
    fun mapToTranslationEntity(): TranslationEntity {
        return TranslationEntity(
            official = official,
            common = common
        )
    }
}
