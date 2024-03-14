package com.wenubey.geoinfo.country.detail

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.country.detail.components.TranslationContent
import com.wenubey.geoinfo.ui.country.detail.components.TranslationHeader
import com.wenubey.geoinfo.ui.country.detail.components.TranslationsInformation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TranslationsInformationTest {

    @get:Rule
    val rule = createComposeRule()

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun translations_is_displayed() {

        // WHEN
        rule.setContent {
            TranslationsInformation()
        }

        // THEN
        rule.onNodeWithTag(context.getString(R.string.translation_information_column_test_tag))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
    }

    @Test
    fun translation_content_is_displayed() {

        // WHEN
        rule.setContent {
            TranslationContent()
        }


        // THEN

        rule.onNodeWithTag(context.getString(R.string.translation_content_column_test_tag, "eng"))
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun is_translation_header_displayed() {
        // GIVEN
        var isOnClickInvoked = false

        // WHEN
        rule.setContent {
            TranslationHeader {
                isOnClickInvoked = true
            }
        }

        // THEN
        rule.onNodeWithTag(context.getString(R.string.translation_header_row_test_tag))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(context.getString(R.string.country_translations_content_description))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(context.getString(R.string.country_translations_expanded_content_description))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(isOnClickInvoked)
    }
}