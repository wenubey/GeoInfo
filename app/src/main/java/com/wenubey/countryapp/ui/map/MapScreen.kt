package com.wenubey.countryapp.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wenubey.countryapp.ui.map.components.GoogleMaps

@Composable
fun MapScreen() {
    Scaffold { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            GoogleMaps(onMapClick = {})
        }

    }
}