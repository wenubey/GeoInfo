package com.wenubey.geoinfo.country.detail

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.country.detail.components.CountryInfoHeader
import com.wenubey.geoinfo.ui.country.detail.components.CountryInfoRow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryInfoRowTest {

    @get:Rule
    val rule = createComposeRule()

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun info_row_is_displayed() {
        // Given: Mock data with already gave as default value for preview

        // When
        rule.setContent {
            CountryInfoRow()
        }

        // Then
        rule
            .onNodeWithContentDescription(context.getString(R.string.preview_content_description))
            .assertExists()
            .assertIsDisplayed()
        rule
            .onNodeWithText(context.getString(R.string.preview_content))
            .assertExists()
            .assertIsDisplayed()
        rule
            .onNodeWithText(context.getString(R.string.preview_header))
            .assertExists()
            .assertIsDisplayed()

    }
}

@RunWith(AndroidJUnit4::class)
class CountryInfoHeaderTest{

    @get:Rule
    val rule = createComposeRule()

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun is_header_displayed() {
        // Given: Mock data with already gave as default value for preview

        // When
        rule.setContent {
            CountryInfoHeader()
        }

        // Then
        rule.onNodeWithText(context.getString(R.string.preview_header))
            .assertExists()
            .assertIsDisplayed()
    }
}