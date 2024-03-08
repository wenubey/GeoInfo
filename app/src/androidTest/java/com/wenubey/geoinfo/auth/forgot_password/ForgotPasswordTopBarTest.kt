package com.wenubey.geoinfo.auth.forgot_password

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.forgot_password.compoents.ForgotPasswordTopBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ForgotPasswordTopBarTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun is_top_bar_visible_and_navigate_back_invoked() {
        var navigateBackInvoked = false

        rule.setContent {
            ForgotPasswordTopBar {
                navigateBackInvoked = true
            }
        }

        rule.onNodeWithTag(context.getString(R.string.forgot_password_top_bar_test_tag))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithTag(context.getString(R.string.forgot_password_top_bar_button_test_tag))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(navigateBackInvoked)
    }
}