package com.wenubey.countryapp.data.local.entities

import androidx.room.Entity
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.domain.model.Currency
import com.wenubey.countryapp.domain.model.Flag
import com.wenubey.countryapp.domain.model.History
import com.wenubey.countryapp.domain.model.Language
import com.wenubey.countryapp.domain.model.NativeName
import com.wenubey.countryapp.utils.Constants.CACHE_TABLE_NAME
import java.util.Date

@Entity(tableName = CACHE_TABLE_NAME)
data class CountryCacheEntity(
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
    val coatOfArmsPng: String?,
    val historyEntity: List<HistoryEntity>?
) {
    fun mapToCountry(): Country {
        return Country(
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
            currency = currencyEntity?.mapToCurrency(),
            region = region,
            subRegion = subRegion,
            language = languageEntity?.mapToLanguage(),
            latlng = latlng,
            area = area,
            flag = flagEntity?.mapToFlag(),
            timezone = timezone,
            coatOfArmsPng = coatOfArmsPng,
            history = historyEntity?.map { it.mapToHistory() },
        )
    }
}

data class NativeNameEntity(
    val common: String?,
    val official: String?
)

data class CurrencyEntity(
    val name: String?,
    val symbol: String?
) {
    fun mapToCurrency(): Currency {
        return Currency(
            name = name,
            symbol = symbol
        )
    }
}

data class LanguageEntity(
    val name: String?
) {
    fun mapToLanguage(): Language {
        return Language(
            name = name
        )
    }
}

data class FlagEntity(
    val png: String?,
    val alt: String?
) {
    fun mapToFlag(): Flag {
        return Flag(
            png = png,
            alt = alt
        )
    }
}


data class HistoryEntity(
    val date: Date?,
    val event: String?
) {
    fun mapToHistory(): History {
        return History(
            date = date,
            event = event
        )
    }
}