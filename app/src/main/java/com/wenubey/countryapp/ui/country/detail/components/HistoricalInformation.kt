package com.wenubey.countryapp.ui.country.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.History
import com.wenubey.countryapp.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricalInformation(
    histories: List<History>,
) {
    var thumbPosition by remember {
        mutableIntStateOf(0)
    }
    var selectedHistory by remember {
        mutableStateOf(histories.first())
    }
    Column {
        InfoHeader(content = Constants.HISTORICAL_INFORMATION)
        Column(
            modifier = Modifier.padding(horizontal = 36.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Slider(
                value = thumbPosition.toFloat(),
                valueRange = 0f..(histories.size - 1).toFloat(),
                steps = histories.size,
                onValueChange = { newValue ->
                    thumbPosition = newValue.toInt()
                    selectedHistory = histories[newValue.toInt()]
                },
                thumb = remember {
                    {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                            ) {
                                Text(
                                    text = selectedHistory.date ?: Constants.UNDEFINED,
                                    modifier = Modifier.padding(4.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                        }
                    }
                }
            )
            Text(
                text = selectedHistory.event ?: Constants.UNDEFINED,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}