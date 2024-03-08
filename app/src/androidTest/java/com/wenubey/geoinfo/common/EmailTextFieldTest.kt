package com.wenubey.geoinfo.common

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.utils.components.EmailTextField
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailTextFieldTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun emailTextField_rendering() {
        // Render the EmailTextField
        val validEmail = "test@example.com"
        rule.setContent {
            EmailTextField(email = TextFieldValue(validEmail))
        }

        rule.onNodeWithTag(context.getString(R.string.email_text_field_test_tag))
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
        rule.onNodeWithTag(context.getString(R.string.email_text_field_test_tag))
            .performTextInput(newEmail)

        // Verify that the callback is triggered with the new email value
        assert(emailValue.text == newEmail)
    }


}