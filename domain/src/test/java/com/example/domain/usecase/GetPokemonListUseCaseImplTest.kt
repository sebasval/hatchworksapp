package com.example.domain.usecase

import app.cash.turbine.test
import com.example.domain.model.Pokemon
import com.example.domain.repository.IPokemonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPokemonListUseCaseImplTest {

    private fun providesRepository(): IPokemonRepository = mockk()

    private fun providesUseCase(
        repository: IPokemonRepository = providesRepository()
    ): GetPokemonListUseCaseImpl = GetPokemonListUseCaseImpl(repository)

    private fun providesPokemon(
        id: Int = 1,
        name: String = "bulbasaur",
        imageUrl: String = "https://example.com/1.png"
    ): Pokemon = Pokemon(id, name, imageUrl)

    private fun providesPokemonList(count: Int = 5): List<Pokemon> =
        (1..count).map { providesPokemon(id = it, name = "pokemon$it") }

    @Test
    fun `invoke returns pokemon list from repository`() = runTest {
        val repository = providesRepository()
        val expectedList = providesPokemonList(3)
        val useCase = providesUseCase(repository)

        coEvery { repository.getPokemonList() } returns flowOf(expectedList)

        useCase().test {
            assertEquals(expectedList, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits empty list when repository returns empty`() = runTest {
        val repository = providesRepository()
        val useCase = providesUseCase(repository)

        coEvery { repository.getPokemonList() } returns flowOf(emptyList())

        useCase().test {
            assertEquals(emptyList<Pokemon>(), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `refresh calls repository refresh`() = runTest {
        val repository = providesRepository()
        val useCase = providesUseCase(repository)

        coEvery { repository.refreshPokemonList() } returns Unit

        useCase.refresh()

        coVerify(exactly = 1) { repository.refreshPokemonList() }
    }
}
