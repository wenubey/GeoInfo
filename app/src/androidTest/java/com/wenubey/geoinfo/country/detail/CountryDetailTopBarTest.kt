package com.wenubey.geoinfo.country.detail

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.country.detail.components.CountryDetailTopBar
import com.wenubey.geoinfo.utils.fakeCountry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CountryDetailTopBarTest {

    @get:Rule
    val rule = createComposeRule()

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun top_bar_is_displayed() {
        // GIVEN
        val country = fakeCountry
        var isNavigateBackInvoked = false

        // WHEN
        rule.setContent {
            CountryDetailTopBar(
                animation = true, country = country,
                navigateBack = {
                    isNavigateBackInvoked = true
                },
            )
        }

        // THEN
        rule
            .onNodeWithContentDescription(context.getString(R.string.country_flag_content_description))
            .assertExists()
            .assertIsDisplayed()

        rule
            .onNodeWithText("Poland")
            .assertExists()
            .assertIsDisplayed()

        rule
            .onNodeWithContentDescription(context.getString(R.string.navigation_back_content_description))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(isNavigateBackInvoked)

    }
}