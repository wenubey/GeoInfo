package com.wenubey.geoinfo.country.detail

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.domain.model.History
import com.wenubey.geoinfo.ui.country.detail.components.HistorySlider
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistorySliderTest {

    @get:Rule
    val rule = createComposeRule()

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun is_slider_displayed() {
        // Given
        val historyList = listOf(
            History(date = "03/11/2024", "Event 1"),
            History(date = "04/11/2024", "Event 2"),
            History(date = "05/11/2024", "Event 3"),
        )

        // When
        rule.setContent {
            HistorySlider(
                histories = historyList
            )
        }

        // Then
        rule
            .onNodeWithText(historyList.first().date!!)
            .assertExists()
            .assertIsDisplayed()

        rule
            .onNodeWithText(historyList.first().event!!)
            .assertExists()
            .assertIsDisplayed()
    }
}