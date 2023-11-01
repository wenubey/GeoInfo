package com.wenubey.countryapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wenubey.countryapp.data.local.entities.CoatOfArmsEntity
import com.wenubey.countryapp.data.local.entities.CurrencyEntity
import com.wenubey.countryapp.data.local.entities.FlagEntity
import com.wenubey.countryapp.data.local.entities.HistoryEntity
import com.wenubey.countryapp.data.local.entities.LanguageEntity

class CountryAppTypeConverter {

    private inline fun <reified T> fromJson(json: String?, typeToken: TypeToken<out T>): T? {
        return if (json == null) null else Gson().fromJson(json, typeToken.type)
    }

    private fun <T> toJson(data: T?): String? {
        return data?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun currencyFromJson(json: String?): CurrencyEntity? =
        fromJson(json, object : TypeToken<CurrencyEntity>() {})

    @TypeConverter
    fun currencyToJson(currencyEntity: CurrencyEntity?): String? =
        toJson(currencyEntity)

    @TypeConverter
    fun languageFromJson(json: String?): LanguageEntity? =
        fromJson(json, object : TypeToken<LanguageEntity>() {})

    @TypeConverter
    fun languageToJson(languageEntity: LanguageEntity?): String? =
        toJson(languageEntity)

    @TypeConverter
    fun flagFromJson(json: String?): FlagEntity? =
        fromJson(json, object : TypeToken<FlagEntity>() {})

    @TypeConverter
    fun flagToJson(flagEntity: FlagEntity?): String? =
        toJson(flagEntity)

    @TypeConverter
    fun coatOfArmsFromJson(json: String?): CoatOfArmsEntity? =
        fromJson(json, object : TypeToken<CoatOfArmsEntity>() {})

    @TypeConverter
    fun coatOfArmsToJson(coatOfArmsEntity: CoatOfArmsEntity?): String? =
        toJson(coatOfArmsEntity)

    @TypeConverter
    fun historyFromJson(json: String?): HistoryEntity? =
        fromJson(json, object : TypeToken<HistoryEntity>() {})

    @TypeConverter
    fun historyToJson(historyEntity: HistoryEntity?): String? =
        toJson(historyEntity)
}