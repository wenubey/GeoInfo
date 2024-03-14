package com.wenubey.geoinfo.profile


import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.profile.components.ImagePicker
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ImagePickerTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun is_image_picker_displayed_click_behavior() {
        rule.setContent {
            ImagePicker()
        }

        rule.onNodeWithTag(context.getString(R.string.image_picker_button_test_tag))
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()


    }
}