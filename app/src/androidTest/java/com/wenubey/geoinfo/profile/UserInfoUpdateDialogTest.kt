package com.wenubey.geoinfo.profile

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.ui.profile.components.UserInfoUpdateDialog
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.wenubey.geoinfo.R

@RunWith(JUnit4::class)
class UserInfoUpdateDialogTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun dialog_is_exists_and_displayed() {
        rule.setContent {
            UserInfoUpdateDialog()
        }

        rule.onNodeWithTag(context.getString(R.string.user_info_update_dialog_test_tag))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithText(context.getString(R.string.profile_info))
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun dialog_text_fields_is_exists_and_showed_values() {
        val testDisplayName = "Test User"

        rule.setContent {
            UserInfoUpdateDialog(
                displayName = TextFieldValue(testDisplayName),
            )
        }

        rule.onNodeWithTag(context.getString(R.string.user_info_update_dialog_display_name_field_test_tag))
            .assertExists()
            .assertIsDisplayed()
            .assertTextContains(testDisplayName)

    }

    @Test
    fun dialog_save_button_behavior() {
        var isSaveButtonInvoked = false

        rule.setContent {
            UserInfoUpdateDialog(
                onClickConfirm = { isSaveButtonInvoked = true }
            )
        }

        rule.onNodeWithTag(context.getString(R.string.user_info_update_dialog_save_button_test_tag))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        assert(isSaveButtonInvoked)
    }
}