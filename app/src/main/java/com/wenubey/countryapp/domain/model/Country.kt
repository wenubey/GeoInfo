package com.wenubey.countryapp.domain.model

import com.wenubey.countryapp.data.local.entities.CountryCacheEntity
import com.wenubey.countryapp.data.local.entities.CountryFavEntity
import com.wenubey.countryapp.data.local.entities.CurrencyEntity
import com.wenubey.countryapp.data.local.entities.FlagEntity
import com.wenubey.countryapp.data.local.entities.HistoryEntity
import com.wenubey.countryapp.data.local.entities.LanguageEntity
import com.wenubey.countryapp.data.local.entities.NativeNameEntity
import java.util.Date

data class Country(
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
    val currency: Currency?,
    val region: String?,
    val subRegion: String?,
    val language: Language?,
    val latlng: List<Double>?,
    val area: Double?,
    val flag: Flag?,
    val timezone: String?,
    val coatOfArmsPng: String?,
    val history: List<History>?
) {
    fun mapToCountryCacheEntity(): CountryCacheEntity {
        return CountryCacheEntity(
            countryCommonName = countryCommonName,
            countryOfficialName = countryOfficialName,
            countryNativeCommonName = countryNativeCommonName,
            countryNativeOfficialName = countryNativeOfficialName,
            capital = capital,
            population = population,
            topLevelDomain = topLevelDomain,
            countryCodeCCA2 = countryCodeCCA2,
            isIndependent = isIndependent,
            isUnMember = isUnMember,
            currencyEntity = currency?.mapToCurrencyEntity(),
            region = region,
            subRegion = subRegion,
            languageEntity = language?.mapToLanguageEntity(),
            latlng = latlng,
            area = area,
            flagEntity = flag?.mapToFlagEntity(),
            timezone = timezone,
            coatOfArmsPng = coatOfArmsPng,
            historyEntity = history?.map { it.mapToHistoryEntity() }
        )
    }

    fun mapToCountryFavEntity(): CountryFavEntity {
        return CountryFavEntity(
            countryCommonName = countryCommonName,
            countryOfficialName = countryOfficialName,
            countryNativeCommonName = countryNativeCommonName,
            countryNativeOfficialName = countryNativeOfficialName,
            capital = capital,
            population = population,
            topLevelDomain = topLevelDomain,
            countryCodeCCA2 = countryCodeCCA2,
            isIndependent = isIndependent,
            isUnMember = isUnMember,
            currencyEntity = currency?.mapToCurrencyEntity(),
            region = region,
            subRegion = subRegion,
            language = language?.mapToLanguageEntity(),
            latlng = latlng,
            area = area,
            flagEntity = flag?.mapToFlagEntity(),
            timezone = timezone,
            coatOfArmsPng = coatOfArmsPng,
            historyEntity = history?.map { it.mapToHistoryEntity() }
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

data class Language(
    val name: String?
) {
    fun mapToLanguageEntity(): LanguageEntity {
        return LanguageEntity(
            name = name
        )
    }
}

data class Flag(
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


data class History(
    val date: Date?,
    val event: String?
) {
    fun mapToHistoryEntity(): HistoryEntity {
        return HistoryEntity(
            date = date,
            event = event
        )
    }
}
