package com.wenubey.countryapp.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.ui.graphics.vector.ImageVector
import com.wenubey.countryapp.utils.Constants.UNDEFINED

fun Double?.formatWithCommasForArea(): String {
    if (this == null) {
        return UNDEFINED
    }
    val formattedValue = String.format("%,.2f", this)
    return if (formattedValue.endsWith(".00")) {
        formattedValue.substring(0, formattedValue.length - 3) + " km²"
    } else {
        "$formattedValue km²"
    }
}

fun Int?.formatWithCommasForPopulation(): String {
    return if (this == null) {
        UNDEFINED
    } else {
        String.format("%,d", this)
    }
}

fun SortOption.toIcon(): ImageVector {
    return when (this) {
        SortOption.NAME -> Icons.Filled.SortByAlpha
        SortOption.AREA -> Icons.Filled.Fullscreen
        SortOption.POPULATION -> Icons.Filled.People
    }
}