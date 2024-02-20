package com.wenubey.geoinfo.auth.verify_email

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wenubey.geoinfo.ui.email_verify.VERIFY_EMAIL_CONTENT_TAG
import com.wenubey.geoinfo.ui.email_verify.VerifyEmailContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VerifyEmailContentTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun emailContent_displayed_and_reloadUser_clicked() {
        var reloadUserInvoked = false
        rule.setContent {
            VerifyEmailContent(
                reloadUser =  { reloadUserInvoked = true}
            )
        }

        rule.onNodeWithTag(VERIFY_EMAIL_CONTENT_TAG)
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithContentDescription("User reload request")
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(reloadUserInvoked)
    }

}