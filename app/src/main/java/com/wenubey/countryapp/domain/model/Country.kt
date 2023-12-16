package com.wenubey.countryapp.domain.model

import com.wenubey.countryapp.data.local.entities.CountryCacheEntity
import com.wenubey.countryapp.data.local.entities.CurrencyEntity
import com.wenubey.countryapp.data.local.entities.HistoryEntity
import com.wenubey.countryapp.data.local.entities.LanguageEntity
import com.wenubey.countryapp.data.local.entities.NativeNameEntity
import com.wenubey.countryapp.data.local.entities.TranslationEntity

data class Country(
    val countryCommonName: String?,
    val countryOfficialName: String?,
    val countryNativeName: Map<String, NativeName>?,
    val capital: List<String>?,
    val population: Int?,
    val topLevelDomain: List<String>?,
    val countryCodeCCA2: String?,
    val currency: Map<String, Currency>?,
    val region: String?,
    val subRegion: String?,
    val language: Map<String, String>,
    val latlng: List<Double>?,
    val area: Double?,
    val flag: Map<String, String>?,
    val timezones: List<String>?,
    val coatOfArms: Map<String, String>?,
    val history: List<History>?,
    val flagEmojiWithPhoneCode: Map<String?, String?>,
    val gini: Map<String?, Double?>,
    val demonyms: Map<String?, Map<String?, String?>?>?,
    val translations: Map<String?, Translation?>,
    val continents: List<String?>?,
    val borders: List<String?>?,
    val isFavorite: Boolean
) {
    fun mapToCountryCacheEntity(): CountryCacheEntity {
        return CountryCacheEntity(
            countryCommonName = countryCommonName,
            countryOfficialName = countryOfficialName,
            countryNativeName = countryNativeName?.mapValues { it.value.mapToNativeNameEntity() },
            capital = capital,
            population = population,
            topLevelDomain = topLevelDomain,
            countryCodeCCA2 = countryCodeCCA2,
            currencyEntity = currency?.mapValues { it.value.mapToCurrencyEntity() },
            region = region,
            subRegion = subRegion,
            languageEntity = LanguageEntity(data = language),
            latlng = latlng,
            area = area,
            flagEntity = flag,
            timezones = timezones,
            coatOfArms = coatOfArms,
            historyEntity = history?.map { it.mapToHistoryEntity() },
            flagEmojiWithPhoneCode = flagEmojiWithPhoneCode,
            gini = gini,
            demonyms = demonyms,
            translations = translations.mapValues { it.value?.mapToTranslationEntity() },
            continents = continents,
            borders = borders,
            isFavorite = isFavorite
        )
    }

}

data class NativeName(
    val common: String?,
    val official: String?
) {
    fun mapToNativeNameEntity(): NativeNameEntity {
        return NativeNameEntity(
            common = common,
            official = official
        )
    }
}

data class Currency(
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

data class History(
    val date: String?,
    val event: String?
) {
    fun mapToHistoryEntity(): HistoryEntity {
        return HistoryEntity(
            date = date,
            event = event
        )
    }
}

data class Translation(
    val official: String?,
    val common: String?,
) {
    fun mapToTranslationEntity(): TranslationEntity {
        return TranslationEntity(
            official = official,
            common = common
        )
    }
}



