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
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {

    private fun providesTestScope(testBody: suspend TestScope.() -> Unit) = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            testBody()
        } finally {
            Dispatchers.resetMain()
        }
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

    private fun providesPokemonListWithNames(): List<Pokemon> = listOf(
        providesPokemon(id = 1, name = "bulbasaur"),
        providesPokemon(id = 2, name = "ivysaur"),
        providesPokemon(id = 3, name = "venusaur"),
        providesPokemon(id = 25, name = "pikachu"),
        providesPokemon(id = 6, name = "charizard")
    )

    @Test
    fun `initial state is loading`() = providesTestScope {
        val useCase = providesUseCase()
        coEvery { useCase.invoke() } returns flowOf(emptyList())

        val viewModel = providesViewModel(useCase)

        assertTrue(viewModel.uiState.value is PokemonListUiState.Loading)
    }

    @Test
    fun `uiState emits success when usecase returns list`() = providesTestScope {
        val useCase = providesUseCase()
        val pokemonList = providesPokemonList(2)
        coEvery { useCase.invoke() } returns flowOf(pokemonList)

        val viewModel = providesViewModel(useCase)
        testScheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is PokemonListUiState.Success)
            assertEquals(pokemonList, (state as PokemonListUiState.Success).pokemonList)
        }
    }

    @Test
    fun `uiState emits error when usecase throws exception`() = providesTestScope {
        val useCase = providesUseCase()
        coEvery { useCase.invoke() } returns flow { throw RuntimeException("Network error") }

        val viewModel = providesViewModel(useCase)
        testScheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is PokemonListUiState.Error)
            assertEquals("Network error", (state as PokemonListUiState.Error).message)
        }
    }

    @Test
    fun `refresh calls usecase refresh`() = providesTestScope {
        val useCase = providesUseCase()
        coEvery { useCase.invoke() } returns flowOf(emptyList())
        coEvery { useCase.refresh() } returns Unit

        val viewModel = providesViewModel(useCase)
        testScheduler.advanceUntilIdle()

        viewModel.refresh()
        testScheduler.advanceUntilIdle()

        coVerify { useCase.refresh() }
    }

    @Test
    fun `featuredPokemon is set when list is loaded`() = providesTestScope {
        val useCase = providesUseCase()
        val pokemonList = providesPokemonList(5)
        coEvery { useCase.invoke() } returns flowOf(pokemonList)

        val viewModel = providesViewModel(useCase)
        testScheduler.advanceUntilIdle()

        viewModel.featuredPokemon.test {
            val featured = awaitItem()
            assertNotNull(featured)
            assertTrue(pokemonList.contains(featured))
        }
    }

    @Test
    fun `searchQuery updates filteredPokemonList`() = providesTestScope {
        val useCase = providesUseCase()
        val pokemonList = providesPokemonListWithNames()
        coEvery { useCase.invoke() } returns flowOf(pokemonList)

        val viewModel = providesViewModel(useCase)
        testScheduler.advanceUntilIdle()

        viewModel.onSearchQueryChanged("pika")
        testScheduler.advanceUntilIdle()

        viewModel.filteredPokemonList.test {
            val filtered = awaitItem()
            assertEquals(1, filtered.size)
            assertEquals("pikachu", filtered[0].name)
        }
    }

    @Test
    fun `searchQuery with id filters by id`() = providesTestScope {
        val useCase = providesUseCase()
        val pokemonList = providesPokemonListWithNames()
        coEvery { useCase.invoke() } returns flowOf(pokemonList)

        val viewModel = providesViewModel(useCase)
        testScheduler.advanceUntilIdle()

        viewModel.onSearchQueryChanged("25")
        testScheduler.advanceUntilIdle()

        viewModel.filteredPokemonList.test {
            val filtered = awaitItem()
            assertEquals(1, filtered.size)
            assertEquals(25, filtered[0].id)
        }
    }

    @Test
    fun `empty searchQuery returns all pokemon`() = providesTestScope {
        val useCase = providesUseCase()
        val pokemonList = providesPokemonListWithNames()
        coEvery { useCase.invoke() } returns flowOf(pokemonList)

        val viewModel = providesViewModel(useCase)
        testScheduler.advanceUntilIdle()

        viewModel.onSearchQueryChanged("pika")
        testScheduler.advanceUntilIdle()
        viewModel.onSearchQueryChanged("")
        testScheduler.advanceUntilIdle()

        viewModel.filteredPokemonList.test {
            val filtered = awaitItem()
            assertEquals(pokemonList.size, filtered.size)
        }
    }

    @Test
    fun `searchQuery is case insensitive`() = providesTestScope {
        val useCase = providesUseCase()
        val pokemonList = providesPokemonListWithNames()
        coEvery { useCase.invoke() } returns flowOf(pokemonList)

        val viewModel = providesViewModel(useCase)
        testScheduler.advanceUntilIdle()

        viewModel.onSearchQueryChanged("BULBA")
        testScheduler.advanceUntilIdle()

        viewModel.filteredPokemonList.test {
            val filtered = awaitItem()
            assertEquals(1, filtered.size)
            assertEquals("bulbasaur", filtered[0].name)
        }
    }
}
