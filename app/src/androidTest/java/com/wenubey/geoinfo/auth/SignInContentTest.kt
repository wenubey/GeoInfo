package com.wenubey.geoinfo.auth

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.sign_in.components.FacebookSignInButton
import com.wenubey.geoinfo.ui.sign_in.components.GoogleSignInButton
import com.wenubey.geoinfo.ui.sign_in.components.SignInContent
import com.wenubey.geoinfo.ui.sign_in.components.XSignInButton
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SignInContentTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    /**
     * Test to ensure that the SignInContent composable is displayed.
     */
    @Test
    fun signInContent_displayed() {
        rule.setContent {
            SignInContent()
        }

        rule.onNodeWithTag(context.getString(R.string.sign_in_column_test_tag))
            .assertIsDisplayed()
    }

    /**
     * Test to ensure that the sign-in button is displayed and clickable.
     * Also verifies that clicking the button triggers the signIn action.
     */
    @Test
    fun signInButton_displayed_clicked() {
        var signInClicked = false

        rule.setContent {
            SignInContent(
                signIn = { _, _ -> signInClicked = true }
            )
        }

        rule.onNodeWithTag(context.getString(R.string.sign_in_button_test_tag))
            .performClick()
            .assertHasClickAction()
            .assertIsDisplayed()

        assert(signInClicked)
    }

    /**
     * Test to ensure that the Google sign-in button is displayed and clickable.
     * Also verifies that clicking the button triggers the signIn action.
     */
    @Test
    fun googleSignInButton_displayed_displayed_clicked() {
        var signInClicked = false

        rule.setContent {
            GoogleSignInButton {
                signInClicked = true
            }
        }

        rule.onNodeWithTag(context.getString(R.string.google_sign_in_button_test_tag))
            .performClick()
            .assertHasClickAction()
            .assertIsDisplayed()

        assert(signInClicked)
    }

    /**
     * Test to ensure that the Facebook sign-in button is displayed and clickable.
     * Also verifies that clicking the button triggers the signIn action.
     */
    @Test
    fun facebookSignInButton_displayed_clicked() {
        var signInClicked = false

        rule.setContent {
            FacebookSignInButton {
                signInClicked = true
            }
        }

        rule.onNodeWithTag(context.getString(R.string.facebook_sign_in_button_test_tag))
            .performClick()
            .assertHasClickAction()
            .assertIsDisplayed()

        assert(signInClicked)
    }

    /**
     * Test to ensure that the X sign-in button is displayed and clickable.
     * Also verifies that clicking the button triggers the signIn action.
     */
    @Test
    fun xSignInButton_displayed_clicked() {
        var signInClicked = false

        rule.setContent {
            XSignInButton {
                signInClicked = true
            }
        }

        rule.onNodeWithTag(context.getString(R.string.x_sign_in_button_test_tag))
            .performClick()
            .assertIsDisplayed()
            .assertHasClickAction()

        assert(signInClicked)
    }

    /**
     * Test to ensure that the "Forgot Password" button is displayed and clickable.
     * Also verifies that clicking the button triggers the navigateToForgotPasswordScreen action.
     */
    @Test
    fun forgotPasswordButton_displayed_clicked() {
        var forgotButtonClicked = false

        rule.setContent {
            SignInContent(
                navigateToForgotPasswordScreen = { forgotButtonClicked = true }
            )
        }

        rule.onNodeWithTag(context.getString(R.string.forgot_password_button_test_tag))
            .performClick()
            .assertHasClickAction()
            .assertIsDisplayed()

        assert(forgotButtonClicked)
    }

    /**
     * Test to ensure that the "Sign Up" button is displayed and clickable.
     * Also verifies that clicking the button triggers the navigateToSignUpScreen action.
     */
    @Test
    fun signUpButton_displayed_clicked() {
        var signUpButtonClicked = false

        rule.setContent {
            SignInContent(
                navigateToSignUpScreen = { signUpButtonClicked = true }
            )
        }

        rule.onNodeWithTag(context.getString(R.string.navigate_to_sign_up_button_test_tag))
            .performClick()
            .assertHasClickAction()
            .assertIsDisplayed()

        assert(signUpButtonClicked)
    }
}
