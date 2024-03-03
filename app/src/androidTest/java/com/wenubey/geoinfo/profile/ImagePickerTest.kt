package com.wenubey.geoinfo.profile


import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.wenubey.geoinfo.ui.profile.components.IMAGE_PICKER_BUTTON_TEST_TAG
import com.wenubey.geoinfo.ui.profile.components.ImagePicker
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ImagePickerTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun is_image_picker_displayed_click_behavior() {
        rule.setContent {
            ImagePicker()
        }

        rule.onNodeWithTag(IMAGE_PICKER_BUTTON_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()


    }
}