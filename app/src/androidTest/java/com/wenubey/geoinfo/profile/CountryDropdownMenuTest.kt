package com.wenubey.geoinfo.profile

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.profile.components.CountryDropdownMenu
import com.wenubey.geoinfo.utils.fakeCountryCodeData
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CountryDropdownMenuTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun row_to_expand_dropdown_menu() {
        // Given: Composable is set up with the CountryDropdownMenu
        rule.setContent {
            CountryDropdownMenu()
        }

        // When: Clicking on the row to expand the dropdown menu
        rule.onNodeWithTag(context.getString(R.string.row_dropdown_test_tag))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        // Then: Dropdown menu should be displayed
        rule.onNodeWithTag(context.getString(R.string.country_dropdown_menu_test_tag))
            .assertExists()
            .assertIsDisplayed()

    }

    @Test
    fun dropdown_menu_search_bar_behavior() {
        // Given: Composable is set up with the CountryDropdownMenu
        //          and also set up the mock user input
        val testText = "TEST"
        rule.setContent {
            CountryDropdownMenu()
        }

        // When: Clicking on the row to expand the dropdown menu
        rule.onNodeWithTag(context.getString(R.string.row_dropdown_test_tag))
            .performClick()

        // Then: Dropdown menu search bar should be displayed and able to input text
        val searchBar = rule.onNodeWithTag(context.getString(R.string.country_dropdown_menu_search_bar_test_tag))
            .assertExists()
            .assertIsDisplayed()

        searchBar.performTextInput(testText)
        searchBar.assertTextContains(testText)

    }

    @Test
    fun dropdown_menu_behavior() {
        // Given: Variables to track selected country
        var selectedCountry: String? = ""
        var selectCountryCodeInvoked = false

        // Given: Composable is set up with the CountryDropdownMenu
        rule.setContent {
            CountryDropdownMenu(
                countryData = fakeCountryCodeData,
                currentCountryCode = fakeCountryCodeData.values.first()!!,
                onSelectCountryCode = {
                    selectCountryCodeInvoked = true
                    selectedCountry = it
                }
            )
        }

        // When: Clicking on an item in the dropdown menu
        rule.onNodeWithTag(context.getString(R.string.row_dropdown_test_tag))
            .performClick()

        rule.onNodeWithText("🇦🇱 Albania")
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        // Then: onSelectCountryCode callback should be invoked with the selected country code
        assert(selectCountryCodeInvoked)
        assert(selectedCountry!!.contains(fakeCountryCodeData["🇦🇱 Albania"]!!))
    }
}