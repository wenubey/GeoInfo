package com.wenubey.geoinfo.country.list

import android.content.Context
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.country.list.CountryList
import com.wenubey.geoinfo.ui.country.list.CountrySearchBar
import com.wenubey.geoinfo.ui.country.list.SortButton
import com.wenubey.geoinfo.utils.fakeCountry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryListComponentsTest {

    @get:Rule
    val rule = createComposeRule()

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun is_sort_button_displayed_and_behavior() {
        // GIVEN
        var isButtonClicked = false

        // WHEN
        rule.setContent {
            SortButton(
                onSortButtonClicked = { _, _ -> isButtonClicked = true }
            )
        }

        // THEN
        rule.onNodeWithTag(context.getString(R.string.sort_button_test_tag))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(isButtonClicked)
    }

    @Test
    fun is_search_bar_displayed_and_behavior() {
        //GIVEN
        val initialValue = mutableStateOf("hello")
        val newValue = "world"
        // WHEN
        rule.setContent {
            CountrySearchBar(
                query = initialValue.value,
                onQueryChange = {
                    initialValue.value = it
                }
            )
        }

        // THEN
        val searchBar =
            rule.onNodeWithTag(context.getString(R.string.country_search_bar_test_tag))

        searchBar
            .assertExists()
            .assertIsDisplayed()
            .assertTextContains(initialValue.value)

        searchBar.performTextClearance()

        searchBar.performTextInput(newValue)

        searchBar.assertTextContains(newValue)

        assert(initialValue.value.contains(newValue))
    }

    @Test
    fun is_country_list_displayed_and_behavior() {
        // GIVEN
        val countries = listOf(
            fakeCountry, fakeCountry, fakeCountry, fakeCountry, fakeCountry,
            fakeCountry, fakeCountry, fakeCountry, fakeCountry, fakeCountry
        )
        val state = LazyListState()

        // WHEN
        rule.setContent {
            CountryList(
                countries = countries,
                lazyListState = state
            )
        }

        // THEN
        rule.onNodeWithTag(context.getString(R.string.country_list_test_tag))
            .assertExists()
            .assertIsDisplayed()
            .assert(hasScrollAction())
            .performScrollToIndex(8)
    }
}