package org.bz.autoorganizer

import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.bz.autoorganizer.root.NavigationTags
import org.bz.autoorganizer.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationComposeTest {

    @get:Rule val composeMainActivityTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAppBarContent() {
        composeMainActivityTestRule.activity.setContent {
            Text(text = "Test AppBar content")
        }

        composeMainActivityTestRule
            .onRoot(useUnmergedTree = false)
            .printToLog("MainActivity")
    }

    @Test
    fun testAppNavigationHost() {
        composeMainActivityTestRule
            .onNodeWithTag(NavigationTags.APP_NAVIGATION_HOST)
            .performClick()

        composeMainActivityTestRule
            .onRoot(useUnmergedTree = false)
            .printToLog("MainActivity")
    }

    @Test
    fun testAddNewAutoButtonClick() {
        composeMainActivityTestRule
            .onNodeWithTag(NavigationTags.ADD_NEW_AUTO_BUTTON)
            .performClick()

        composeMainActivityTestRule
            .onRoot(useUnmergedTree = false)
            .printToLog("MainActivity")
    }
}