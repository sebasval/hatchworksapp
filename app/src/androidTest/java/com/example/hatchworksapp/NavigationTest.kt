package com.example.hatchworksapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun providesFirstPokemonId(): String = "#001"

    private fun providesTimeoutMillis(): Long = 15000L

    private fun waitForPokemonListToLoad() {
        composeTestRule.waitUntil(timeoutMillis = providesTimeoutMillis()) {
            composeTestRule
                .onAllNodesWithText(providesFirstPokemonId())
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun navigateToFirstPokemonDetail() {
        waitForPokemonListToLoad()
        composeTestRule.onNodeWithText(providesFirstPokemonId()).performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun navigation_startsAtPokemonList() {
        composeTestRule.onNodeWithText("Pokédex").assertIsDisplayed()
    }

    @Test
    fun navigation_pokemonDetailHasBackButton() {
        navigateToFirstPokemonDetail()

        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }

    @Test
    fun navigation_backFromDetailReturnsToPokemonList() {
        navigateToFirstPokemonDetail()

        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Pokédex").assertIsDisplayed()
    }

    @Test
    fun navigation_clickFirstPokemon_showsDetailScreen() {
        navigateToFirstPokemonDetail()

        composeTestRule.waitUntil(timeoutMillis = providesTimeoutMillis()) {
            composeTestRule
                .onAllNodesWithText("Bulbasaur")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
        composeTestRule.onNodeWithText("Base Stats").assertIsDisplayed()
        composeTestRule.onNodeWithText("Info").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }
}
