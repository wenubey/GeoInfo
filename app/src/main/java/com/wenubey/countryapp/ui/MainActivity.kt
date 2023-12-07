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
import com.wenubey.countryapp.ui.navigation.NavGraph
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import org.koin.compose.KoinContext


class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoinContext {
                CountryAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        navHostController = rememberNavController()
                        NavGraph(
                            navHostController = navHostController,
                        )
                        AuthState(navHostController = navHostController)
                    }
                }
            }
        }
    }
}




