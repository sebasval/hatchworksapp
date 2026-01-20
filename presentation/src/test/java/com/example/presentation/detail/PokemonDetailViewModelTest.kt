package com.example.presentation.detail

import app.cash.turbine.test
import com.example.domain.model.PokemonDetail
import com.example.domain.model.PokemonStat
import com.example.domain.usecase.IGetPokemonDetailUseCase
import io.mockk.coEvery
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
class PokemonDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun providesUseCase(): IGetPokemonDetailUseCase = mockk(relaxed = true)

    private fun providesViewModel(
        pokemonId: Int = 1,
        useCase: IGetPokemonDetailUseCase = providesUseCase()
    ): PokemonDetailViewModel = PokemonDetailViewModel(pokemonId, useCase)

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
        stats = listOf(PokemonStat("hp", 45)),
        abilities = listOf("overgrow")
    )

    @Test
    fun `initial state is loading`() = runTest {
        val useCase = providesUseCase()
        coEvery { useCase.invoke(1) } returns flowOf(null)

        val viewModel = providesViewModel(1, useCase)

        assertTrue(viewModel.uiState.value is PokemonDetailUiState.Loading)
    }

    @Test
    fun `uiState emits success when usecase returns detail`() = runTest {
        val useCase = providesUseCase()
        val pokemonDetail = providesPokemonDetail()
        coEvery { useCase.invoke(1) } returns flowOf(pokemonDetail)

        val viewModel = providesViewModel(1, useCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is PokemonDetailUiState.Success)
            assertEquals(pokemonDetail, (state as PokemonDetailUiState.Success).pokemonDetail)
        }
    }

    @Test
    fun `uiState emits error when usecase returns null`() = runTest {
        val useCase = providesUseCase()
        coEvery { useCase.invoke(999) } returns flowOf(null)

        val viewModel = providesViewModel(999, useCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is PokemonDetailUiState.Error)
            assertEquals("Pokemon not found", (state as PokemonDetailUiState.Error).message)
        }
    }

    @Test
    fun `uiState emits error when usecase throws exception`() = runTest {
        val useCase = providesUseCase()
        coEvery { useCase.invoke(1) } returns flow { throw RuntimeException("Network error") }

        val viewModel = providesViewModel(1, useCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is PokemonDetailUiState.Error)
            assertEquals("Network error", (state as PokemonDetailUiState.Error).message)
        }
    }
}
