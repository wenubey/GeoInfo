package com.wenubey.geoinfo.auth.verify_email

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.ui.email_verify.VerifyEmailContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.wenubey.geoinfo.R

@RunWith(AndroidJUnit4::class)
class VerifyEmailContentTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun emailContent_displayed_and_reloadUser_clicked() {
        var reloadUserInvoked = false
        rule.setContent {
            VerifyEmailContent(
                reloadUser =  { reloadUserInvoked = true}
            )
        }

        rule.onNodeWithTag(context.getString(R.string.verify_email_content_test_tag))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithContentDescription(context.getString(R.string.user_reload_request_cd))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(reloadUserInvoked)
    }

}