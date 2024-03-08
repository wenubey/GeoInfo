package com.wenubey.geoinfo.common

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.utils.components.PasswordTextField
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PasswordTextFieldTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun passwordTextField_rendering() {
        // Render the PasswordTextField
        val validPassword = "ValidPassword123$"
        rule.setContent {
            PasswordTextField(
                password = TextFieldValue(validPassword),
                isPasswordVisible = mutableStateOf(true)
            )
        }

        rule.onNodeWithTag(context.getString(R.string.password_text_field_test_tag))
            .assertIsDisplayed()
            .assertTextContains(validPassword)
    }

    @Test
    fun passwordTextField_behavior() {
        var passwordValue = TextFieldValue("")

        // Render the PasswordTextField with a callback
        rule.setContent {
            PasswordTextField(
                password = passwordValue,
                onPasswordValueChange = { value, _ ->
                    passwordValue = value
                }
            )
        }

        val newPassword = "newPassword123$"

        rule.onNodeWithTag(context.getString(R.string.password_text_field_test_tag))
            .performTextInput(newPassword)

        assert(passwordValue.text == newPassword)
    }
}