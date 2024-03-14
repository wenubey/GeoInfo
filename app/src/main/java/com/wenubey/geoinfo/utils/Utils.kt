package com.wenubey.geoinfo.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wenubey.geoinfo.domain.model.Country
import com.wenubey.geoinfo.domain.model.Currency
import com.wenubey.geoinfo.domain.model.History
import com.wenubey.geoinfo.domain.model.NativeName
import com.wenubey.geoinfo.domain.model.Translation
import com.wenubey.geoinfo.domain.model.toUser
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun parseEventDate(day: String?, month: String?, year: String?): String? {
    return try {
        val isBC = year?.startsWith('-') == true
        val absYear = year?.removePrefix("-")?.toIntOrNull() ?: 0

        val formattedYear = if (isBC) {
            "BC $absYear"
        } else {
            absYear.toString()
        }
        return "$day/$month/$formattedYear"
    } catch (e: ParseException) {
        null
    }
}

fun addUserToFirestore(
    auth: FirebaseAuth,
    db: FirebaseFirestore,
    authProvider: AuthProvider,
    createdAt: String? = null,
    isPhoneNumberVerified: Boolean? = false,
    favCountries: Map<String, String>? = null,
) {
    auth.currentUser?.apply {
        val user = toUser(authProvider, createdAt = createdAt, isPhoneNumberVerified = isPhoneNumberVerified, favCountries = favCountries)
        db.collection(Utils.USERS).document(uid).set(user)
    }
}


class Utils {
    companion object {

        const val USERS = "users"
        const val TAG = "utilsTAG"
        fun printLog(e: Exception) = Log.e(TAG, e.stackTraceToString())

        fun Context.makeToast(message: String?) =
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }
}

fun getCurrentTime(): String {
    val currentTime = Calendar.getInstance().time
    val pattern = "dd/MM/yyyy - HH:mm"
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(currentTime)
}

enum class AuthProvider {
    EMAIL,
    GOOGLE,
    FACEBOOK,
    TWITTER
}

val countryNameMapping = mapOf(
    "Türkiye" to "Turkey",
    "The Bahamas" to "Bahamas"
)

fun normalizeCountryName(countryName: String?): String? {
    return countryNameMapping[countryName] ?: countryName
}

val fakeCountryCodeData = mapOf<String?, String?>(
    "🇦🇩 Andorra" to "+376",
    "🇦🇱 Albania" to "+355",
    "🇦🇪 United Arab Emirates" to "+971",
    "🇦🇫 Afghanistan" to "+93",
    "🇦🇬 Antigua and Barbuda" to "+1268",
    "🇦🇮 Anguilla" to "+1264",
    "🇦🇲 Armenia" to "+374",
    "🇦🇴 Angola" to "+244",
    "🇦🇷 Argentina" to "+54",
    "🇲🇽 Mexico" to "+52",
    "🇲🇾 Malaysia" to "+60",
    "🇲🇿 Mozambique" to "+258"
)

val fakeCountry = Country(
    countryCommonName = "Poland",
    countryOfficialName = "Poland Republic",
    countryNativeName = mapOf(
        "pol" to NativeName(common = "Polska", official = "Rzeczpospolita Polska")
    ),
    topLevelDomain = listOf(".pl"),
    countryCodeCCA2 = "PL",
    currency = mapOf(
        "PLN" to Currency(name = "Polish złoty", symbol = "zł")
    ),
    capital = listOf("Warsaw"),
    region = "Europe",
    subRegion = "Central Europe",
    language = mapOf(
        "pol" to "Polish",
        "tur" to "Turkish",
        "eng" to "English",
        "ger" to "German",
    ),
    latlng = LatLng(52.0, 20.0),
    area = 312679.0,
    flag = mapOf(
        "png" to "https://flagcdn.com/w320/pl.png",
        "alt" to "The flag of Poland is composed of two equal horizontal bands of white and red."
    ),
    population = 37950802,
    timezones = listOf("UTC+01:00"),
    coatOfArms = mapOf(
        "png" to "https://mainfacts.com/media/images/coats_of_arms/pl.png"
    ),
    flagEmojiWithPhoneCode = mapOf(
        "🇵🇱 Poland" to "+48"
    ),
    history = listOf(
        History("01/30/1018", "Poland and Holy Roman Empire conclude the Peace of Bautzen."),
        History("01/20/1320", "Duke Wladyslaw Lokietek becomes king of Poland."),
    ),
    demonyms = mapOf(
        "eng" to mapOf("f" to "Polish", "m" to "Polish")
    ),
    translations = mapOf(
        "tur" to Translation(official = "Polonya Cumhuriyeti", common = "Polonya"),
        "eng" to Translation(official = "Republic of Poland", common = "Poland"),
        "fra" to Translation(official = "République de Pologne", common = "Pologne"),
        "deu" to Translation(official = "Republik Polen", common = "Polen"),
    ),
    gini = mapOf(
        "2018" to 30.2
    ),
    continents = listOf(
        "Europe"
    ),
    borders = listOf(
        "TR",
        "DE"
    ),
    isFavorite = false,
    flagEmoji = "\uD83C\uDDF5\uD83C\uDDF1"
)
val fakeCountryCode = mapOf<String?, String?>("\uD83C\uDDF5\uD83C\uDDF1" to "+48")