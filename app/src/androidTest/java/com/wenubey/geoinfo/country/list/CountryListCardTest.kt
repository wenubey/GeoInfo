package com.wenubey.geoinfo.country.list

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.country.list.AreaPopulationRow
import com.wenubey.geoinfo.ui.country.list.CountryFlag
import com.wenubey.geoinfo.ui.country.list.CountryInfoColumn
import com.wenubey.geoinfo.utils.fakeCountry
import com.wenubey.geoinfo.utils.formatWithCommasForArea
import com.wenubey.geoinfo.utils.formatWithCommasForPopulation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CountryListCardTest {

    @get:Rule
    val rule = createComposeRule()

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun is_country_flag_displayed() {
        // GIVEN
        val country = fakeCountry

        // WHEN
        rule.setContent {
            CountryFlag(country = country)
        }

        // THEN
        rule.onNodeWithContentDescription(context.getString(R.string.country_flag_content_description))
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun is_country_info_column_displayed_and_behavior() {
        // GIVEN
        var isFavButtonInvoked = false
        val country = fakeCountry

        // WHEN
        rule.setContent {
            CountryInfoColumn(country = country, onFavButtonClicked = { _, _ ->
                isFavButtonInvoked = true
            })
        }

        // THEN
        rule.onNodeWithText(country.countryCommonName!!)
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(context.getString(R.string.add_fav_content_description))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(isFavButtonInvoked)
    }

    @Test
    fun area_population_row_is_displayed() {
        // GIVEN
        val country = fakeCountry

        // WHEN
        rule.setContent {
            AreaPopulationRow(country = country)
        }

        // THEN
        rule.onNodeWithText(country.population.formatWithCommasForPopulation())
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithText(country.area.formatWithCommasForArea())
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(context.getString(R.string.area_content_description))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(context.getString(R.string.population_content_description))
            .assertExists()
            .assertIsDisplayed()
    }
}