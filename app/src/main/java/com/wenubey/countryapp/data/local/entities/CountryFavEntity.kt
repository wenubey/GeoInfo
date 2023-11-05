package com.wenubey.countryapp.data.local.entities

import androidx.room.Entity
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.utils.Constants.FAV_TABLE_NAME

@Entity(tableName = FAV_TABLE_NAME)
data class CountryFavEntity(
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
    val language: LanguageEntity?,
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
            language = language?.mapToLanguage(),
            latlng = latlng,
            area = area,
            flag = flagEntity?.mapToFlag(),
            timezone = timezone,
            coatOfArmsPng = coatOfArmsPng,
            history = historyEntity?.map { it.mapToHistory() },
        )
    }
}
