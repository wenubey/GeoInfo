package com.wenubey.countryapp.ui.country.detail

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wenubey.countryapp.ui.country.CountryViewModel
import com.wenubey.countryapp.ui.country.detail.components.CountryDetailTopBar
import com.wenubey.countryapp.ui.country.list.CountryEvent
import com.wenubey.countryapp.utils.Utils.Companion.makeToast
import com.wenubey.countryapp.utils.components.ProgressBar
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

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(country.flag?.get("png"))
                    .build(),
                placeholder = rememberVectorPainter(image = Icons.Default.Flag),

                )
            Scaffold(
                topBar = {
                    CountryDetailTopBar(
                        animation = animationTopBar,
                        painter = painter,
                        country = country,
                        navigateBack = navigateBack
                    )
                },
                content = { paddingValues ->
                    CountryDetailContent(
                        country = country,
                        paddingValues = paddingValues,
                        lazyListState = lazyListState,
                        painter = painter,
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


