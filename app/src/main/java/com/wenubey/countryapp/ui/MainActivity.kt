package com.wenubey.countryapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.wenubey.countryapp.ui.deep_link.DeepLinkViewModel
import com.wenubey.countryapp.ui.navigation.NavGraph
import com.wenubey.countryapp.ui.tab_screen.TabViewModel
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext


class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    private val deepLinkViewModel: DeepLinkViewModel by viewModel()
    private val tabViewModel: TabViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoinContext {
                CountryAppTheme {

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        navController = rememberNavController()
                        deepLinkViewModel.handleDeepLink(intent, navController)
                        NavGraph(
                            navHostController = navController,
                            tabViewModel = tabViewModel
                        )
                        AuthState(navController = navController)
                    }
                }
            }
        }
    }
}




