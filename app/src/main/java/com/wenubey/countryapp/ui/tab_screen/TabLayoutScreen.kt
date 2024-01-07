package com.wenubey.countryapp.ui.tab_screen

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.R
import com.wenubey.countryapp.ui.country.list.CountryListScreen
import com.wenubey.countryapp.ui.map.MapScreen
import com.wenubey.countryapp.ui.profile.ProfileScreen
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants


@Composable
fun TabLayoutScreen(
    navigateToCountryDetailScreen: (countryCode: String?, countryName: String?) -> Unit,
    navigateToForgotPasswordScreen: (email: String) -> Unit,
    navigateToSignInScreen: () -> Unit,
    countryName: String,
    tabViewModel: TabViewModel
) {
    TabLayoutContent(
        navigateToCountryDetailScreen = navigateToCountryDetailScreen,
        navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
        navigateToSignInScreen = navigateToSignInScreen,
        countryName = countryName,
        tabViewModel = tabViewModel
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabLayoutContent(
    navigateToCountryDetailScreen: (countryCode: String?, countryName: String?) -> Unit = { _, _ -> },
    navigateToForgotPasswordScreen: (email: String) -> Unit = {},
    navigateToSignInScreen: () -> Unit = {},
    countryName: String = "",
    tabViewModel: TabViewModel,
) {

    val snackBarHostState = SnackbarHostState()

    var selectedTabIndex by tabViewModel.currentIndex
    val pagerState = rememberPagerState {
        tabs.size
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
        tabViewModel.setIndex(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(4.dp)
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            tabViewModel.setIndex(index)
                        },
                        text = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedTabIndex) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = stringResource(id = R.string.TABS_CONTENT_DESCRIPTION)
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState,
                userScrollEnabled = selectedTabIndex != 0,
            ) { index ->
                when (index) {
                    0 -> {
                        MapScreen(
                            navigateToCountryDetailScreen = navigateToCountryDetailScreen,
                            countryName = countryName,
                        )
                    }
                    1 -> {
                        CountryListScreen(
                            navigateToCountryDetailScreen = navigateToCountryDetailScreen,
                        )
                    }
                    2 -> {
                            ProfileScreen(
                                navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
                                snackBarHostState = snackBarHostState,
                                navigateToCountryDetailScreen = navigateToCountryDetailScreen,
                                navigateToSignInScreen = navigateToSignInScreen
                            )
                    }
                    else -> {}
                }
            }
        }

    }
}

private val tabs = listOf(
    TabItem(
        title = Constants.MAP_SCREEN_TITLE,
        selectedIcon = Icons.Filled.Map,
        unselectedIcon = Icons.Outlined.Map
    ),
    TabItem(
        title = Constants.COUNTRY_LIST_SCREEN_TITLE,
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    ),
    TabItem(
        title = Constants.PROFILE_SCREEN_TITLE,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
    )
)

data class TabItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun TabLayoutContentPreview() {
    CountryAppTheme {
        Surface {
            //TabLayoutContent()
        }
    }
}