package com.wenubey.countryapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.domain.model.Currency
import com.wenubey.countryapp.domain.model.History
import com.wenubey.countryapp.domain.model.NativeName
import com.wenubey.countryapp.domain.model.Translation
import com.wenubey.countryapp.utils.Constants.CACHE_TABLE_NAME

@Entity(tableName = CACHE_TABLE_NAME)
data class CountryCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "countryCommonName")
    val countryCommonName: String?,
    @ColumnInfo(name = "countryOfficialName")
    val countryOfficialName: String?,
    @ColumnInfo(name = "countryNativeName")
    val countryNativeName: Map<String, NativeNameEntity>?,
    @ColumnInfo(name = "capital")
    val capital: List<String>?,
    @ColumnInfo(name = "population")
    val population: Int?,
    @ColumnInfo(name = "topLevelDomain")
    val topLevelDomain: List<String>?,
    @ColumnInfo(name = "countryCodeCCA2")
    val countryCodeCCA2: String?,
    @ColumnInfo(name = "currencyEntity")
    val currencyEntity: Map<String,CurrencyEntity>?,
    @ColumnInfo(name = "region")
    val region: String?,
    @ColumnInfo(name = "subRegion")
    val subRegion: String?,
    @ColumnInfo(name = "languageEntity")
    val languageEntity: LanguageEntity?,
    @ColumnInfo(name = "latlng")
    val latlng: LatLng?,
    @ColumnInfo(name = "area")
    val area: Double?,
    @ColumnInfo(name = "flagEntity")
    val flagEntity: Map<String,String>?,
    @ColumnInfo(name = "timezones")
    val timezones: List<String>?,
    @ColumnInfo(name = "coatOfArms")
    val coatOfArms: Map<String,String>?,
    @ColumnInfo(name = "historyEntity")
    val historyEntity: List<HistoryEntity>?,
    @ColumnInfo(name = "flagEmojiWithPhoneCode")
    val flagEmojiWithPhoneCode: Map<String?,String?>,
    @ColumnInfo(name = "gini")
    val gini: Map<String?, Double?>,
    @ColumnInfo(name = "demonyms")
    val demonyms: Map<String?, Map<String?, String?>?>?,
    @ColumnInfo(name = "translations")
    val translations: Map<String?, TranslationEntity?>,
    @ColumnInfo(name = "continents")
    val continents: List<String?>?,
    @ColumnInfo(name = "borders")
    val borders: List<String?>?,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean
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
            language = languageEntity?.data ?: mapOf(),
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
            borders = borders,
            isFavorite = isFavorite
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
    val date: String?,
    val event: String?
) {
    fun mapToHistory(): History {
        return History(
            date = date,
            event = event
        )
    }
}

data class TranslationEntity(
    val official: String?,
    val common: String?,
) {
    fun mapToTranslation(): Translation {
        return Translation(
            official = official,
            common = common
        )
    }
}

data class LanguageEntity(
    val data: Map<String, String>?
)

