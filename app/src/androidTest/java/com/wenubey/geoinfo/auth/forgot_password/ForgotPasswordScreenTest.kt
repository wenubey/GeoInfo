package com.wenubey.geoinfo.auth.forgot_password

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.forgot_password.compoents.ForgotPasswordContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ForgotPasswordScreenTest {
    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun screen_is_displayed() {

        rule.setContent {
            ForgotPasswordContent()
        }

        rule.onNodeWithTag(context.getString(R.string.forgot_password_content_test_tag))
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

        rule.onNodeWithTag(context.getString(R.string.forgot_password_content_test_tag))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithText(context.getString(R.string.reset_password))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(sendUserResetMailInvoked)

    }
}