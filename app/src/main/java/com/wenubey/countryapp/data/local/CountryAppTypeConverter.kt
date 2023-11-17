package com.wenubey.countryapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wenubey.countryapp.data.local.entities.CurrencyEntity
import com.wenubey.countryapp.data.local.entities.HistoryEntity
import com.wenubey.countryapp.data.local.entities.NativeNameEntity

class CountryAppTypeConverter {

    private inline fun <reified T> fromJson(json: String?, typeToken: TypeToken<out T>): T? {
        return if (json == null) null else Gson().fromJson(json, typeToken.type)
    }

    private fun <T> toJson(data: T?): String? {
        return data?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun mapStringsFromJson(json: String?): Map<String, String>? =
        fromJson(json, object : TypeToken<Map<String,String>>() {})

    @TypeConverter
    fun mapStringsToJson(data: Map<String,String>?): String? =
        toJson(data)

    @TypeConverter
    fun nativeNameFromJson(json: String?): Map<String,NativeNameEntity>? =
        fromJson(json, object : TypeToken<Map<String, NativeNameEntity>>() {})

    @TypeConverter
    fun nativeNameToJson(nativeName: Map<String, NativeNameEntity>?): String? =
        toJson(nativeName)

    @TypeConverter
    fun latLngFromJson(json: String?): List<Double>? =
        fromJson(json, object : TypeToken<List<Double>>() {})

    @TypeConverter
    fun latLngToJson(latLng: List<Double>?): String? =
        toJson(latLng)

    @TypeConverter
    fun currencyFromJson(json: String?): Map<String,CurrencyEntity>? =
        fromJson(json, object : TypeToken<Map<String,CurrencyEntity>>() {})

    @TypeConverter
    fun currencyToJson(currencyEntity: Map<String,CurrencyEntity>?): String? =
        toJson(currencyEntity)

    @TypeConverter
    fun historyFromJson(json: String?): List<HistoryEntity>? =
        fromJson(json, object : TypeToken<List<HistoryEntity>>() {})

    @TypeConverter
    fun historyToJson(historyEntity: List<HistoryEntity>?): String? =
        toJson(historyEntity)

    @TypeConverter
    fun capitalFromJson(json: String?): List<String>? =
        fromJson(json, object :TypeToken<List<String>>() {})

    @TypeConverter
    fun capitalToJson(capital: List<String>?): String? =
        toJson(capital)

    @TypeConverter
    fun countryPhoneFromJson(json: String?): Map<String?,String?>? =
        fromJson(json, object : TypeToken<Map<String?,String?>>() {})

    @TypeConverter
    fun countryPhoneToJson(countryPhone: Map<String?,String?>?): String? =
        toJson(countryPhone)
}