package com.wenubey.geoinfo.profile

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.ui.profile.components.AccountSettingsMenu
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

        rule.onNodeWithTag(context.getString(R.string.settings_menu_icon_button_test_tag))
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

        rule.onNodeWithTag(context.getString(R.string.settings_menu_icon_button_test_tag))
            .performClick()

        rule.onNodeWithTag(context.getString(R.string.sign_out))
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

        rule.onNodeWithTag(context.getString(R.string.settings_menu_icon_button_test_tag))
            .performClick()

        rule.onNodeWithTag(context.getString(R.string.revoke_access))
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

        rule.onNodeWithTag(context.getString(R.string.settings_menu_icon_button_test_tag))
            .performClick()

        rule.onNodeWithTag(context.getString(R.string.forgot_password))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(navigateToForgotPasswordScreenInvoked)

    }
}