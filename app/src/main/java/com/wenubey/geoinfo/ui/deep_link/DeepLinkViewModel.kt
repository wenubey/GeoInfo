package com.wenubey.geoinfo.ui.deep_link

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class DeepLinkViewModel(private val deepLinkFacade: DeepLinkFacade): ViewModel() {

    fun handleDeepLink(intent: Intent?, navHostController: NavHostController) {
        deepLinkFacade.handleDeepLink(intent,navHostController)
    }

    fun sendDeepLink(countryCode: String, countryName: String, context: Context) {
        deepLinkFacade.sendDeepLink(countryCode, countryName, context)
    }

    fun goToWikipedia(countryName: String?, context: Context) {
        deepLinkFacade.goToWikipedia(countryName, context)
    }
}