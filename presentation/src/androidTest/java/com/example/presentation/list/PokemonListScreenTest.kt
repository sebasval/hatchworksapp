package com.example.presentation.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.domain.model.Pokemon
import com.example.domain.usecase.IGetPokemonListUseCase
import com.example.presentation.theme.HatchWorksAppTheme
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class PokemonListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun providesUseCase(): IGetPokemonListUseCase = mockk(relaxed = true)

    private fun providesPokemon(
        id: Int = 1,
        name: String = "bulbasaur"
    ): Pokemon = Pokemon(id, name, "https://example.com/$id.png")

    private fun providesPokemonList(count: Int = 3): List<Pokemon> =
        (1..count).map { providesPokemon(id = it, name = "pokemon$it") }

    private fun providesViewModel(
        useCase: IGetPokemonListUseCase = providesUseCase()
    ): PokemonListViewModel = PokemonListViewModel(useCase)

    @Test
    fun pokemonListScreen_displaysPokemonList() {
        val useCase = providesUseCase()
        val pokemonList = providesPokemonList(3)
        coEvery { useCase.invoke() } returns flowOf(pokemonList)

        val viewModel = providesViewModel(useCase)

        composeTestRule.setContent {
            HatchWorksAppTheme {
                PokemonListScreen(
                    onPokemonClick = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Pokédex").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pokemon1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pokemon2").assertIsDisplayed()
    }

    @Test
    fun pokemonListScreen_clickOnPokemon_triggersCallback() {
        val useCase = providesUseCase()
        val pokemonList = listOf(providesPokemon(id = 25, name = "pikachu"))
        coEvery { useCase.invoke() } returns flowOf(pokemonList)

        val viewModel = providesViewModel(useCase)
        var clickedId: Int? = null

        composeTestRule.setContent {
            HatchWorksAppTheme {
                PokemonListScreen(
                    onPokemonClick = { clickedId = it },
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Pikachu").performClick()

        assert(clickedId == 25)
    }

    @Test
    fun pokemonListScreen_displaysEmptyState() {
        val useCase = providesUseCase()
        coEvery { useCase.invoke() } returns flowOf(emptyList())

        val viewModel = providesViewModel(useCase)

        composeTestRule.setContent {
            HatchWorksAppTheme {
                PokemonListScreen(
                    onPokemonClick = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Pokédex").assertIsDisplayed()
    }
}
