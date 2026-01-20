package com.example.hatchworksapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainActivity_launchesSuccessfully() {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            assert(activity != null)
        }
    }

    @Test
    fun mainActivity_displaysPokemonListTitle() {
        composeTestRule.onNodeWithText("Pokédex").assertIsDisplayed()
    }
}
