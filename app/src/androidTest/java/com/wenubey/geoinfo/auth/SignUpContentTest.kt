package com.wenubey.geoinfo.auth

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wenubey.geoinfo.ui.sign_up.components.NAVIGATE_TO_SIGN_IN_BUTTON
import com.wenubey.geoinfo.ui.sign_up.components.SIGN_UP_BUTTON
import com.wenubey.geoinfo.ui.sign_up.components.SIGN_UP_COLUMN
import com.wenubey.geoinfo.ui.sign_up.components.SignUpContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpContentTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun signUpContent_displayed() {
        rule.setContent {
            SignUpContent()
        }

        rule.onNodeWithTag(SIGN_UP_COLUMN)
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

        rule.onNodeWithTag(SIGN_UP_BUTTON)
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

        rule.onNodeWithTag(NAVIGATE_TO_SIGN_IN_BUTTON)
            .performClick()
            .assertHasClickAction()
            .assertIsDisplayed()

        assert(signInButtonClicked)
    }
}