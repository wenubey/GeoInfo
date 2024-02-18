package com.wenubey.geoinfo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wenubey.geoinfo.utils.components.EMAIL_TEXT_FIELD_TEST_TAG
import com.wenubey.geoinfo.utils.components.EmailTextField
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailTextFieldTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun emailTextField_rendering() {
        // Render the EmailTextField
        val validEmail = "test@example.com"
        rule.setContent {
            EmailTextField(email = TextFieldValue(validEmail))
        }

        rule.onNodeWithTag(EMAIL_TEXT_FIELD_TEST_TAG)
            .assertIsDisplayed()
            .assertTextContains(validEmail)
    }

    @Test
    fun emailTextField_behavior() {
        var emailValue = TextFieldValue("")

        // Render the EmailTextField with a callback
        rule.setContent {
            EmailTextField(
                email = emailValue,
                onEmailValueChange = { value, _ ->
                    emailValue = value
                }
            )
        }

        val newEmail = "newemail@example.com"

        // Enter a new email
        rule.onNodeWithTag(EMAIL_TEXT_FIELD_TEST_TAG)
            .performTextInput(newEmail)

        // Verify that the callback is triggered with the new email value
        assert(emailValue.text == newEmail)
    }


}