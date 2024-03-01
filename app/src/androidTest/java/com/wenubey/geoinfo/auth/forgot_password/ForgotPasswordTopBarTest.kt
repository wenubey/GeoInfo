package com.wenubey.geoinfo.auth.forgot_password

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.wenubey.geoinfo.ui.forgot_password.compoents.FORGOT_PASSWORD_TOP_BAR_BUTTON_TEST_TAG
import com.wenubey.geoinfo.ui.forgot_password.compoents.FORGOT_PASSWORD_TOP_BAR_TEST_TAG
import com.wenubey.geoinfo.ui.forgot_password.compoents.ForgotPasswordTopBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ForgotPasswordTopBarTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun is_top_bar_visible_and_navigate_back_invoked() {
        var navigateBackInvoked = false

        rule.setContent {
            ForgotPasswordTopBar {
                navigateBackInvoked = true
            }
        }

        rule.onNodeWithTag(FORGOT_PASSWORD_TOP_BAR_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithTag(FORGOT_PASSWORD_TOP_BAR_BUTTON_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(navigateBackInvoked)
    }
}