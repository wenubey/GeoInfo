package com.wenubey.geoinfo.profile

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.ui.profile.components.AccountSettingsMenu
import com.wenubey.geoinfo.ui.profile.components.SETTINGS_MENU_ICON_BUTTON_TEST_TAG
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.wenubey.geoinfo.R

@RunWith(JUnit4::class)
class AccountSettingsMenuTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun is_dropdown_menu_visible() {

        rule.setContent {
            AccountSettingsMenu()
        }

        rule.onNodeWithTag(SETTINGS_MENU_ICON_BUTTON_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

    }

    @Test
    fun menu_item_sign_out_behavior() {
        var signOutInvoked = false

        rule.setContent {
            AccountSettingsMenu(
                signOut = { signOutInvoked = true },
            )
        }

        rule.onNodeWithTag(SETTINGS_MENU_ICON_BUTTON_TEST_TAG)
            .performClick()

        rule.onNodeWithTag(context.getString(R.string.SIGN_OUT))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(signOutInvoked)

    }

    @Test
    fun menu_item_revoke_access_behavior() {
        var revokeAccessInvoked = false

        rule.setContent {
            AccountSettingsMenu(
                revokeAccess = { revokeAccessInvoked = true },
            )
        }

        rule.onNodeWithTag(SETTINGS_MENU_ICON_BUTTON_TEST_TAG)
            .performClick()

        rule.onNodeWithTag(context.getString(R.string.REVOKE_ACCESS))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(revokeAccessInvoked)

    }

    @Test
    fun menu_item_navigate_forgot_password_behavior() {
        var navigateToForgotPasswordScreenInvoked = false

        rule.setContent {
            AccountSettingsMenu(
                navigateToForgotPasswordScreen = { navigateToForgotPasswordScreenInvoked = true },
            )
        }

        rule.onNodeWithTag(SETTINGS_MENU_ICON_BUTTON_TEST_TAG)
            .performClick()

        rule.onNodeWithTag(context.getString(R.string.FORGOT_PASSWORD))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(navigateToForgotPasswordScreenInvoked)

    }
}