package com.wenubey.countryapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.domain.model.Currency
import com.wenubey.countryapp.domain.model.History
import com.wenubey.countryapp.domain.model.NativeName
import com.wenubey.countryapp.utils.Constants.CACHE_TABLE_NAME
import java.util.Date

@Entity(tableName = CACHE_TABLE_NAME)
data class CountryCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val countryCommonName: String?,
    val countryOfficialName: String?,
    val countryNativeName: Map<String, NativeNameEntity>?,
    val capital: List<String>?,
    val population: Double?,
    val topLevelDomain: List<String>?,
    val countryCodeCCA2: String?,
    val isIndependent: Boolean?,
    val isUnMember: Boolean?,
    val currencyEntity: Map<String,CurrencyEntity>?,
    val region: String?,
    val subRegion: String?,
    val languageEntity: Map<String, String>?,
    val latlng: List<Double>?,
    val area: Double?,
    val flagEntity: Map<String,String>?,
    val timezones: List<String>?,
    val coatOfArms: Map<String,String>?,
    val historyEntity: List<HistoryEntity>?
) {
    fun mapToCountry(): Country {
        return Country(
            countryCommonName = countryCommonName,
            countryOfficialName = countryOfficialName,
            countryNativeName = countryNativeName?.mapValues { it.value.mapToNativeName() },
            capital = capital,
            population = population,
            topLevelDomain = topLevelDomain,
            countryCodeCCA2 = countryCodeCCA2,
            isIndependent = isIndependent,
            isUnMember = isUnMember,
            currency = currencyEntity?.mapValues { it.value.mapToCurrency() },
            region = region,
            subRegion = subRegion,
            language = languageEntity,
            latlng = latlng,
            area = area,
            flag = flagEntity,
            timezones = timezones,
            coatOfArms = coatOfArms,
            history = historyEntity?.map { it.mapToHistory() },
        )
    }
}

data class NativeNameEntity(
    val common: String?,
    val official: String?
) {
    fun mapToNativeName(): NativeName {
        return NativeName(
            common = common,
            official = official
        )
    }
}

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

