package com.wenubey.geoinfo.auth.forgot_password

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wenubey.geoinfo.ui.forgot_password.compoents.FORGOT_PASSWORD_TEST_TAG
import com.wenubey.geoinfo.ui.forgot_password.compoents.ForgotPasswordContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ForgotPasswordScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun screen_is_displayed() {

        rule.setContent {
            ForgotPasswordContent()
        }

        rule.onNodeWithTag(FORGOT_PASSWORD_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun reset_button_is_clickable_invoked() {
        val userEmail = "test@example.com"
        var sendUserResetMailInvoked = false


        rule.setContent {
            ForgotPasswordContent(
                userEmail = userEmail,
                sendPasswordResetMail = {
                    sendUserResetMailInvoked = true
                }
            )
        }

        rule.onNodeWithTag(FORGOT_PASSWORD_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithText("Reset")
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(sendUserResetMailInvoked)

    }
}