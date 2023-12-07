package com.wenubey.countryapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.utils.Constants.FAV_TABLE_NAME

@Entity(tableName = FAV_TABLE_NAME)
data class CountryFavEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val countryCommonName: String?,
    val countryOfficialName: String?,
    val countryNativeName: Map<String, NativeNameEntity>?,
    val capital: List<String>?,
    val population: Int?,
    val topLevelDomain: List<String>?,
    val countryCodeCCA2: String?,
    val currencyEntity: Map<String,CurrencyEntity>?,
    val region: String?,
    val subRegion: String?,
    val language: LanguageEntity?,
    val latlng: List<Double>?,
    val area: Double?,
    val flagEntity: Map<String,String>?,
    val timezones: List<String>?,
    val coatOfArms: Map<String,String>?,
    val historyEntity: List<HistoryEntity>?,
    val flagEmojiWithPhoneCode: Map<String?,String?>,
    val gini: Map<String?, Double?>,
    val demonyms: Map<String?, Map<String?, String?>?>?,
    val translations: Map<String?, TranslationEntity?>,
    val continents: List<String?>?,
    val borders: List<String?>?,
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
            currency = currencyEntity?.mapValues { it.value.mapToCurrency() },
            region = region,
            subRegion = subRegion,
            language = language?.data,
            latlng = latlng,
            area = area,
            flag = flagEntity,
            timezones = timezones,
            coatOfArms = coatOfArms,
            history = historyEntity?.map { it.mapToHistory() },
            flagEmojiWithPhoneCode = flagEmojiWithPhoneCode,
            gini = gini,
            demonyms = demonyms,
            translations = translations.mapValues { it.value?.mapToTranslation() },
            continents = continents,
            borders = borders
        )
    }
}
