package com.wenubey.geoinfo.country.detail

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.country.detail.components.CurrencyRow
import com.wenubey.geoinfo.ui.country.detail.components.DemonymsRow
import com.wenubey.geoinfo.utils.fakeCountry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EconomicalInfoTest {

    @get:Rule
    val rule = createComposeRule()

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext


    @Test
    fun is_currency_row_displayed() {
        // Given
        val country = fakeCountry

        // When
        rule.setContent {
            CurrencyRow(country = country)
        }

        // Then
        rule.onNodeWithText(context.getString(R.string.currencies))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithText("Polish złoty (PLN)")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun is_demonyms_row_displayed() {
        // Given
        val country = fakeCountry.copy(
            demonyms = mapOf(
                "Turkish" to mapOf("f" to "Turkish Woman", "m" to "Turkish Man")
            )
        )

        // When
        rule.setContent {
            DemonymsRow(country = country)
        }

        rule.onNodeWithText(context.getString(R.string.demonyms))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithText("Turkish Woman")
            .assertExists()
            .assertIsDisplayed()
    }
}