package com.wenubey.countryapp.domain.model

import com.wenubey.countryapp.data.local.entities.CountryCacheEntity
import com.wenubey.countryapp.data.local.entities.CountryFavEntity
import com.wenubey.countryapp.data.local.entities.CurrencyEntity
import com.wenubey.countryapp.data.local.entities.HistoryEntity
import com.wenubey.countryapp.data.local.entities.NativeNameEntity
import java.util.Date

data class Country(
    val countryCommonName: String?,
    val countryOfficialName: String?,
    val countryNativeName: Map<String, NativeName>?,
    val capital: List<String>?,
    val population: Double?,
    val topLevelDomain: List<String>?,
    val countryCodeCCA2: String?,
    val isIndependent: Boolean?,
    val isUnMember: Boolean?,
    val currency: Map<String,Currency>?,
    val region: String?,
    val subRegion: String?,
    val language: Map<String, String>?,
    val latlng: List<Double>?,
    val area: Double?,
    val flag: Map<String,String>?,
    val timezones: List<String>?,
    val coatOfArms: Map<String,String>?,
    val history: List<History>?
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
            isIndependent = isIndependent,
            isUnMember = isUnMember,
            currencyEntity = currency?.mapValues { it.value.mapToCurrencyEntity() },
            region = region,
            subRegion = subRegion,
            languageEntity = language,
            latlng = latlng,
            area = area,
            flagEntity = flag,
            timezones = timezones,
            coatOfArms = coatOfArms,
            historyEntity = history?.map { it.mapToHistoryEntity() }
        )
    }

    fun mapToCountryFavEntity(): CountryFavEntity {
        return CountryFavEntity(
            countryCommonName = countryCommonName,
            countryOfficialName = countryOfficialName,
            countryNativeName = countryNativeName?.mapValues { it.value.mapToNativeNameEntity() },
            capital = capital,
            population = population,
            topLevelDomain = topLevelDomain,
            countryCodeCCA2 = countryCodeCCA2,
            isIndependent = isIndependent,
            isUnMember = isUnMember,
            currencyEntity = currency?.mapValues { it.value.mapToCurrencyEntity() },
            region = region,
            subRegion = subRegion,
            language = language,
            latlng = latlng,
            area = area,
            flagEntity = flag,
            timezones = timezones,
            coatOfArms = coatOfArms,
            historyEntity = history?.map { it.mapToHistoryEntity() }
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


