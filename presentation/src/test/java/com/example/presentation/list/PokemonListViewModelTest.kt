package com.example.presentation.list

import app.cash.turbine.test
import com.example.domain.model.Pokemon
import com.example.domain.usecase.IGetPokemonListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun providesUseCase(): IGetPokemonListUseCase = mockk(relaxed = true)

    private fun providesViewModel(
        useCase: IGetPokemonListUseCase = providesUseCase()
    ): PokemonListViewModel = PokemonListViewModel(useCase)

    private fun providesPokemon(
        id: Int = 1,
        name: String = "bulbasaur"
    ): Pokemon = Pokemon(id, name, "https://example.com/$id.png")

    private fun providesPokemonList(count: Int = 3): List<Pokemon> =
        (1..count).map { providesPokemon(id = it, name = "pokemon$it") }

    @Test
    fun `initial state is loading`() = runTest {
        val useCase = providesUseCase()
        coEvery { useCase.invoke() } returns flowOf(emptyList())

        val viewModel = providesViewModel(useCase)

        assertTrue(viewModel.uiState.value is PokemonListUiState.Loading)
    }

    @Test
    fun `uiState emits success when usecase returns list`() = runTest {
        val useCase = providesUseCase()
        val pokemonList = providesPokemonList(2)
        coEvery { useCase.invoke() } returns flowOf(pokemonList)

        val viewModel = providesViewModel(useCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is PokemonListUiState.Success)
            assertEquals(pokemonList, (state as PokemonListUiState.Success).pokemonList)
        }
    }

    @Test
    fun `uiState emits error when usecase throws exception`() = runTest {
        val useCase = providesUseCase()
        coEvery { useCase.invoke() } returns flow { throw RuntimeException("Network error") }

        val viewModel = providesViewModel(useCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is PokemonListUiState.Error)
            assertEquals("Network error", (state as PokemonListUiState.Error).message)
        }
    }

    @Test
    fun `refresh calls usecase refresh`() = runTest {
        val useCase = providesUseCase()
        coEvery { useCase.invoke() } returns flowOf(emptyList())
        coEvery { useCase.refresh() } returns Unit

        val viewModel = providesViewModel(useCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.refresh()
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { useCase.refresh() }
    }
}
