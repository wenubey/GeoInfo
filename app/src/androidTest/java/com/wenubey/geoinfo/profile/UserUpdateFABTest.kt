package com.wenubey.geoinfo.profile

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.wenubey.geoinfo.ui.profile.components.UserUpdateFAB
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.wenubey.geoinfo.R

@RunWith(JUnit4::class)
class UserUpdateFABTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun is_FAB_exists_and_showed() {
        var isButtonClicked = false
        rule.setContent {
            UserUpdateFAB(
                onClick = { isButtonClicked = true}
            )
        }

        rule.onNodeWithTag(context.getString(R.string.user_update_fab_test_tag))
            .assertExists()
            .assertIsDisplayed()

        rule.onNodeWithTag(context.getString(R.string.user_update_fab_test_tag))
            .assertHasClickAction()
            .performClick()

        assert(isButtonClicked)
    }



}