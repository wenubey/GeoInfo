package com.wenubey.geoinfo.profile

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.domain.model.User
import com.wenubey.geoinfo.ui.profile.components.User
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.profile.components.FAV_COUNTRIES_ROW_TEST_TAG
import com.wenubey.geoinfo.ui.profile.components.FavCountriesRow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext


    private val fakeUser = User(
        displayName = "TestName",
        email = "hello@world.com",
        phoneNumber = "+123456789",
        createdAt = "03/03/2024",
        photoUri = null,
        favCountries = mapOf(
            "🇦🇷 Argentina" to "+54",
            "🇲🇽 Mexico" to "+52",
            "🇲🇾 Malaysia" to "+60",
        )
    )

    @Test
    fun user_photo_displayed() {
        rule.setContent {
            User(
                user = fakeUser
            )
        }

        rule.onNodeWithContentDescription(context.getString(R.string.PROFILE_PHOTO_DESCRIPTION))
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun user_fields_is_displayed() {
        rule.setContent {
            User(
                user = fakeUser
            )
        }

        rule.onNodeWithContentDescription(context.getString(R.string.DISPLAY_NAME_CONTENT_DESCRIPTION))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(context.getString(R.string.EMAIL_CONTENT_DESCRIPTION))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(context.getString(R.string.PHONE_NUMBER_CONTENT_DESCRIPTION))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(context.getString(R.string.CREATED_AT_CONTENT_DESCRIPTION))
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun fav_countries_row_is_displayed_and_row_item_behavior() {
        var navigateToCountryDetailScreenInvoked = false

        rule.setContent {
            FavCountriesRow(
                favCountries = fakeUser.favCountries,
                navigateToCountryDetailScreen = { _, _ ->
                    navigateToCountryDetailScreenInvoked = true
                },
            )
        }

        rule.onNodeWithTag(FAV_COUNTRIES_ROW_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithText("🇲🇾 Malaysia")
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(navigateToCountryDetailScreenInvoked)
    }
}