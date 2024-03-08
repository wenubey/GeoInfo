package com.wenubey.geoinfo.auth

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.ui.sign_up.components.SignUpContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.wenubey.geoinfo.R

@RunWith(AndroidJUnit4::class)
class SignUpContentTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun signUpContent_displayed() {
        rule.setContent {
            SignUpContent()
        }

        rule.onNodeWithTag(context.getString(R.string.sign_up_column_test_tag))
            .assertIsDisplayed()
    }

    @Test
    fun signUpButton_displayed_clicked() {

        var signUpButtonClicked = false

        rule.setContent {
            SignUpContent(
                signUp = { _, _ -> signUpButtonClicked = true}
            )
        }

        rule.onNodeWithTag(context.getString(R.string.sign_up_button_test_tag))
            .performClick()
            .assertHasClickAction()
            .assertIsDisplayed()

        assert(signUpButtonClicked)
    }

    @Test
    fun navigateToSignInButton_displayed_clicked() {

        var signInButtonClicked = false

        rule.setContent {
            SignUpContent(
                navigateToSignInScreen = { signInButtonClicked = true}
            )
        }

        rule.onNodeWithTag(context.getString(R.string.navigate_to_sign_in_button_test_tag))
            .performClick()
            .assertHasClickAction()
            .assertIsDisplayed()

        assert(signInButtonClicked)
    }
}