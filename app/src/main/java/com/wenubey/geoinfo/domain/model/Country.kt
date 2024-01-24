package com.wenubey.geoinfo.domain.model

import com.google.android.gms.maps.model.LatLng

data class Country(
    val countryCommonName: String?,
    val countryOfficialName: String?,
    val countryNativeName: Map<String, NativeName>?,
    val capital: List<String>?,
    val population: Int?,
    val topLevelDomain: List<String>?,
    val countryCodeCCA2: String?,
    val currency: Map<String, Currency>?,
    val region: String?,
    val subRegion: String?,
    val language: Map<String, String>,
    val latlng: LatLng?,
    val area: Double?,
    val flag: Map<String, String>?,
    val timezones: List<String>?,
    val coatOfArms: Map<String, String>?,
    val history: List<History>?,
    val flagEmojiWithPhoneCode: Map<String?, String?>,
    val gini: Map<String?, Double?>,
    val demonyms: Map<String?, Map<String?, String?>?>?,
    val translations: Map<String?, Translation?>,
    val continents: List<String?>?,
    val borders: List<String?>?,
    val isFavorite: Boolean,
    val flagEmoji: String?,
)

data class NativeName(
    val common: String?,
    val official: String?
)

data class Currency(
    val name: String?,
    val symbol: String?
)

data class History(
    val date: String?,
    val event: String?
)

data class Translation(
    val official: String?,
    val common: String?,
)



