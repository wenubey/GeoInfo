package com.wenubey.geoinfo.common

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wenubey.geoinfo.utils.components.PASSWORD_TEXT_FIELD_TEST_TAG
import com.wenubey.geoinfo.utils.components.PasswordTextField
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PasswordTextFieldTest {

    @get:Rule
    val rule = createComposeRule()

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

        rule.onNodeWithTag(PASSWORD_TEXT_FIELD_TEST_TAG)
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

        rule.onNodeWithTag(PASSWORD_TEXT_FIELD_TEST_TAG)
            .performTextInput(newPassword)

        assert(passwordValue.text == newPassword)
    }
}