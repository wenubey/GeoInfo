package com.wenubey.countryapp.data.local.entities

import androidx.room.ColumnInfo
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
    @ColumnInfo(name = "isIndependent")
    val isIndependent: Boolean?,
    @ColumnInfo(name = "isUnMember")
    val isUnMember: Boolean?,
    @ColumnInfo(name = "currencyEntity")
    val currencyEntity: Map<String,CurrencyEntity>?,
    @ColumnInfo(name = "region")
    val region: String?,
    @ColumnInfo(name = "subRegion")
    val subRegion: String?,
    @ColumnInfo(name = "languageEntity")
    val languageEntity: Map<String, String>?,
    @ColumnInfo(name = "latlng")
    val latlng: List<Double>?,
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
    val flagEmojiWithPhoneCode: Map<String?,String?>
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
            flagEmojiWithPhoneCode = flagEmojiWithPhoneCode
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

