package com.wenubey.countryapp.ui.country.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.domain.model.Country
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.country.detail.components.BasicInformation
import com.wenubey.countryapp.ui.country.detail.components.CountryDetailTopBar
import com.wenubey.countryapp.ui.country.detail.components.CulturalInformation
import com.wenubey.countryapp.ui.country.detail.components.EconomicInformation
import com.wenubey.countryapp.ui.country.detail.components.FlagWithCountryCommonName
import com.wenubey.countryapp.ui.country.detail.components.GeographicalInformation
import com.wenubey.countryapp.ui.country.detail.components.HistoricalInformation
import com.wenubey.countryapp.ui.country.detail.components.TranslationsInformation
import com.wenubey.countryapp.ui.country.list.CountryEvent
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Utils.Companion.makeToast
import com.wenubey.countryapp.utils.components.ProgressBar
import com.wenubey.countryapp.utils.fakeCountry
import org.koin.androidx.compose.koinViewModel


@Composable
fun CountryDetailScreen(
    countryName: String?,
    countryCode: String?,
    navigateBack: () -> Unit,
    navigateToMapScreen: (countryName: String?) -> Unit,
    countryViewModel: CountryViewModel = koinViewModel()
) {
    countryViewModel.onEvent(
        CountryEvent.OnGetCountry(
            countryName = countryName!!,
            countryCode = countryCode!!
        )
    )

    val context = LocalContext.current

    val lazyListState = rememberLazyListState()
    val animationTopBar by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex != 0
        }
    }

    countryViewModel.countryDataState.let { state ->
        if (state.country != null) {
            val country = state.country
            Scaffold(
                topBar = {
                    CountryDetailTopBar(
                        animation = animationTopBar,
                        country = country,
                        navigateBack = navigateBack
                    )
                },
                content = { paddingValues ->
                    CountryDetailContent(
                        country = country,
                        paddingValues = paddingValues,
                        lazyListState = lazyListState,
                        languages = countryViewModel.countryLanguageNames,
                        navigateToMapScreen = navigateToMapScreen,
                    )
                }
            )
        } else if (state.isLoading) {
            ProgressBar()
        } else if (!state.error.isNullOrBlank()) {
            //TODO Make error screen
            context.makeToast(state.error)
        }
    }
}

@Composable
private fun CountryDetailContent(
    country: Country = fakeCountry,
    paddingValues: PaddingValues = PaddingValues(),
    lazyListState: LazyListState = rememberLazyListState(),
    languages: Map<String, String> = fakeCountry.language,
    navigateToMapScreen: (countryName: String?) -> Unit = {}
) {
    LazyColumn(
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(paddingValues)
            .padding(4.dp)
    ) {
        item {
            FlagWithCountryCommonName(
                country = country,
                navigateToMapScreen = navigateToMapScreen
            )
        }
        item {
            BasicInformation(country = country)
            GeographicalInformation(country = country)
            CulturalInformation(country = country)
            EconomicInformation(country = country)
            HistoricalInformation(histories = country.history ?: emptyList())
            TranslationsInformation(country = country, languages = languages)
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun CountryDetailContentPreview() {
    CountryAppTheme {
        Surface {
            CountryDetailContent()
        }
    }
}



