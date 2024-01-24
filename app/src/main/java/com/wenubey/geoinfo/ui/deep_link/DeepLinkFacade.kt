package com.wenubey.geoinfo.ui.deep_link

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavHostController
import com.wenubey.geoinfo.ui.navigation.Screen
import com.wenubey.geoinfo.utils.Constants
import com.wenubey.geoinfo.utils.formatFromUri
import com.wenubey.geoinfo.utils.formatToUri

interface DeepLinkFacade {
    fun handleDeepLink(intent: Intent?, navHostController: NavHostController)

    fun sendDeepLink(countryCode: String, countryName: String, context: Context)

    fun goToWikipedia(countryName: String?, context: Context)
}

class DeepLinkFacadeImpl: DeepLinkFacade {
    override fun handleDeepLink(intent: Intent?, navHostController: NavHostController) {
        if (intent?.action == Intent.ACTION_VIEW) {
            intent.data?.let { uri ->
                val pathSegments = uri.pathSegments
                if (pathSegments.size >= 3 && pathSegments[0] == "detail") {
                    val countryCode = pathSegments[1]
                    val countryName = pathSegments[2]
                    navHostController.navigate(Screen.CountryDetailScreen.route + "/${countryCode}/${countryName.formatFromUri()}")
                }
            }
        }
    }

    override fun sendDeepLink(countryCode: String, countryName: String, context: Context) {


        val deepLink = Constants.BASE_URL_DEEP_LINK + "/${countryCode}/${countryName.formatToUri()}"
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out information about $countryName on Geoinfo App!\n${deepLink}"
            )
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }

    override fun goToWikipedia(countryName: String?, context: Context) {
        if (countryName != null) {
            val wikipediaUrl = Constants.BASE_URL_WIKIPEDIA + countryName
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikipediaUrl))
            context.startActivity(intent)
        }
    }
}