package com.example.presentation.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.domain.model.PokemonDetail
import com.example.domain.model.PokemonStat
import com.example.domain.usecase.IGetPokemonDetailUseCase
import com.example.presentation.theme.HatchWorksAppTheme
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class PokemonDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun providesUseCase(): IGetPokemonDetailUseCase = mockk(relaxed = true)

    private fun providesPokemonDetail(
        id: Int = 1,
        name: String = "bulbasaur"
    ): PokemonDetail = PokemonDetail(
        id = id,
        name = name,
        imageUrl = "https://example.com/$id.png",
        height = 7,
        weight = 69,
        types = listOf("grass", "poison"),
        stats = listOf(
            PokemonStat("hp", 45),
            PokemonStat("attack", 49)
        ),
        abilities = listOf("overgrow")
    )

    private fun providesViewModel(
        pokemonId: Int = 1,
        useCase: IGetPokemonDetailUseCase = providesUseCase()
    ): PokemonDetailViewModel = PokemonDetailViewModel(pokemonId, useCase)

    @Test
    fun pokemonDetailScreen_displaysPokemonDetail() {
        val useCase = providesUseCase()
        val pokemonDetail = providesPokemonDetail(id = 1, name = "bulbasaur")
        coEvery { useCase.invoke(1) } returns flowOf(pokemonDetail)

        val viewModel = providesViewModel(1, useCase)

        composeTestRule.setContent {
            HatchWorksAppTheme {
                PokemonDetailScreen(
                    pokemonId = 1,
                    onBackClick = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
        composeTestRule.onNodeWithText("#001").assertIsDisplayed()
        composeTestRule.onNodeWithText("Grass").assertIsDisplayed()
        composeTestRule.onNodeWithText("Poison").assertIsDisplayed()
    }

    @Test
    fun pokemonDetailScreen_displaysStats() {
        val useCase = providesUseCase()
        val pokemonDetail = providesPokemonDetail()
        coEvery { useCase.invoke(1) } returns flowOf(pokemonDetail)

        val viewModel = providesViewModel(1, useCase)

        composeTestRule.setContent {
            HatchWorksAppTheme {
                PokemonDetailScreen(
                    pokemonId = 1,
                    onBackClick = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Base Stats").assertIsDisplayed()
        composeTestRule.onNodeWithText("HP").assertIsDisplayed()
        composeTestRule.onNodeWithText("45").assertIsDisplayed()
    }

    @Test
    fun pokemonDetailScreen_displaysHeightAndWeight() {
        val useCase = providesUseCase()
        val pokemonDetail = providesPokemonDetail()
        coEvery { useCase.invoke(1) } returns flowOf(pokemonDetail)

        val viewModel = providesViewModel(1, useCase)

        composeTestRule.setContent {
            HatchWorksAppTheme {
                PokemonDetailScreen(
                    pokemonId = 1,
                    onBackClick = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Info").assertIsDisplayed()
        composeTestRule.onNodeWithText("0.7 m").assertIsDisplayed()
        composeTestRule.onNodeWithText("6.9 kg").assertIsDisplayed()
    }

    @Test
    fun pokemonDetailScreen_backButton_triggersCallback() {
        val useCase = providesUseCase()
        val pokemonDetail = providesPokemonDetail()
        coEvery { useCase.invoke(1) } returns flowOf(pokemonDetail)

        val viewModel = providesViewModel(1, useCase)
        var backClicked = false

        composeTestRule.setContent {
            HatchWorksAppTheme {
                PokemonDetailScreen(
                    pokemonId = 1,
                    onBackClick = { backClicked = true },
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        assert(backClicked)
    }

    @Test
    fun pokemonDetailScreen_displaysErrorWhenPokemonNotFound() {
        val useCase = providesUseCase()
        coEvery { useCase.invoke(999) } returns flowOf(null)

        val viewModel = providesViewModel(999, useCase)

        composeTestRule.setContent {
            HatchWorksAppTheme {
                PokemonDetailScreen(
                    pokemonId = 999,
                    onBackClick = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Pokemon not found").assertIsDisplayed()
    }
}
