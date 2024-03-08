package com.wenubey.geoinfo.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.google.android.gms.maps.model.LatLng
import com.wenubey.geoinfo.R

@Composable
fun Double?.formatWithCommasForArea(): String {
    if (this == null) {
        return stringResource(id = R.string.undefined)
    }
    val formattedValue = String.format("%,.2f", this)
    return if (formattedValue.endsWith(".00")) {
        formattedValue.substring(0, formattedValue.length - 3) + " km²"
    } else {
        "$formattedValue km²"
    }
}

@Composable
fun Int?.formatWithCommasForPopulation(): String {
    return if (this == null) {
        stringResource(id = R.string.undefined)
    } else {
        String.format("%,d", this)
    }
}

fun SortOption.toIcon(): ImageVector {
    return when (this) {
        SortOption.NAME -> Icons.Filled.SortByAlpha
        SortOption.AREA -> Icons.Filled.Fullscreen
        SortOption.POPULATION -> Icons.Filled.People
        SortOption.FAV -> Icons.Filled.Star
    }
}

fun String.formatToUri() :String = if (this.contains(" ")) this.replace(" ", "_") else this

fun String.formatFromUri(): String = if (this.contains("_")) this.replace("_", " ") else this

fun List<Double>?.getLatLngFromRemote(): LatLng {
    return LatLng(this?.get(0) ?: 0.0, this?.get(1) ?: 0.0)
}

fun TextFieldValue.emailVerifier() : Boolean = !(this.text.contains("@") && this.text.contains(".com")) && this.text.isNotBlank()

fun TextFieldValue.passwordVerifier(): Boolean = !(this.text.length > 6 &&
        Regex("[!@#\$%^&*(),.?\":{}|<>\\[\\]\\\\/_-]").containsMatchIn(this.text) &&
        Regex("[A-Z]").containsMatchIn(this.text) &&
        Regex("\\d").containsMatchIn(this.text)) && this.text.isNotBlank()
