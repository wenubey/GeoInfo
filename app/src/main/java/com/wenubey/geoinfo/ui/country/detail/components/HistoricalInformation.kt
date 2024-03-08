package com.wenubey.geoinfo.ui.country.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.domain.model.History
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme
import com.wenubey.geoinfo.utils.fakeCountry


@Composable
fun HistoricalInformation(
    histories: List<History>,
) {
    HistoryContent(histories = histories)
}


@Composable
private fun HistoryContent(
    histories: List<History> = fakeCountry.history!!,
) {
    Column {
        InfoHeader(header = stringResource(id= R.string.historical_information))
        HistorySlider(histories = histories)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistorySlider(
    histories: List<History> = fakeCountry.history!!
) {
    var thumbPosition by remember {
        mutableIntStateOf(0)
    }
    var selectedHistory by remember {
        mutableStateOf(histories.first())
    }
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
                                text = selectedHistory.date ?: stringResource(id = R.string.undefined),
                                modifier = Modifier.padding(4.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                    }
                }
            }
        )
        Text(
            text = selectedHistory.event ?: stringResource(id = R.string.undefined),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun HistoryContentPreview() {
     GeoInfoAppTheme {
        Surface {
             HistoryContent()
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun HistorySliderPreview() {
    GeoInfoAppTheme {
        Surface {
            HistorySlider()
        }
    }
}