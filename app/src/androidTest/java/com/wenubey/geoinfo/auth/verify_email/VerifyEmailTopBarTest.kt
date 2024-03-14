package com.wenubey.geoinfo.auth.verify_email

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.ui.email_verify.VerifyEmailTopBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.wenubey.geoinfo.R

@RunWith(AndroidJUnit4::class)
class VerifyEmailTopBarTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun topBar_displayed() {

        rule.setContent {
            VerifyEmailTopBar()
        }

        rule.onNodeWithTag(context.getString(R.string.verify_email_top_bar_test_tag))
            .assertIsDisplayed()
    }

    @Test
    fun topBar_behavior() {
        var isMenuOpened by mutableStateOf(false)
        var navigateBackInvoked = false

        rule.setContent {
            VerifyEmailTopBar(
                isMenuOpened = isMenuOpened,
                onMenuOpenedClicked = { isMenuOpened = !isMenuOpened },
                navigateBack = { navigateBackInvoked = true }
            )
        }

        // Verify the title text
        rule.onNodeWithText(context.getString(R.string.verify_screen_title)).assertExists()

        // Verify the navigation icon
        // Simulate clicking on the navigation icon
        rule.onNodeWithContentDescription(context.getString(R.string.back_button_description)).assertExists()
            .performClick()

        // Verify that navigateBack func is invoked
        assert(navigateBackInvoked)

        // Verify the actions icon
        rule.onNodeWithContentDescription(context.getString(R.string.open_menu_description))
            .assertExists()
            .performClick()

        // Verify that the menu is displayed
        rule.onNodeWithTag(context.getString(R.string.verify_email_top_bar_menu_test_tag)).assertExists()


    }

    @Test
    fun topBarMenuSignOutButtonItem_displayed_clicked() {
        var signOutInvoked = false

        rule.setContent {
            VerifyEmailTopBar(
                signOut = { signOutInvoked = true },
                isMenuOpened = true
            )
        }

        rule.onNodeWithContentDescription(context.getString(R.string.sign_out))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(signOutInvoked)
    }

    @Test
    fun topBarMenuRevokeAccessItem_displayed_clicked() {
        var revokeAccessInvoked = false

        rule.setContent {
            VerifyEmailTopBar(
                revokeAccess = { revokeAccessInvoked = true },
                isMenuOpened = true
            )
        }

        rule.onNodeWithContentDescription(context.getString(R.string.revoke_access))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(revokeAccessInvoked)
    }

    @Test
    fun topBarMenuForgotPasswordItem_displayed_clicked() {
        var forgotPasswordInvoked = false

        rule.setContent {
            VerifyEmailTopBar(
                navigateToForgotPasswordScreen = { forgotPasswordInvoked = true },
                isMenuOpened = true
            )
        }

        rule.onNodeWithContentDescription(context.getString(R.string.forgot_password))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(forgotPasswordInvoked)
    }

}