package com.example.domain.usecase

import app.cash.turbine.test
import com.example.domain.model.PokemonDetail
import com.example.domain.model.PokemonStat
import com.example.domain.repository.IPokemonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GetPokemonDetailUseCaseImplTest {

    private fun providesRepository(): IPokemonRepository = mockk()

    private fun providesUseCase(
        repository: IPokemonRepository = providesRepository()
    ): GetPokemonDetailUseCaseImpl = GetPokemonDetailUseCaseImpl(repository)

    private fun providesPokemonDetail(
        id: Int = 1,
        name: String = "bulbasaur",
        imageUrl: String = "https://example.com/1.png",
        height: Int = 7,
        weight: Int = 69,
        types: List<String> = listOf("grass", "poison"),
        stats: List<PokemonStat> = listOf(
            PokemonStat("hp", 45),
            PokemonStat("attack", 49)
        ),
        abilities: List<String> = listOf("overgrow")
    ): PokemonDetail = PokemonDetail(id, name, imageUrl, height, weight, types, stats, abilities)

    @Test
    fun `invoke returns pokemon detail from repository`() = runTest {
        val repository = providesRepository()
        val expectedDetail = providesPokemonDetail()
        val useCase = providesUseCase(repository)

        coEvery { repository.getPokemonDetail(1) } returns flowOf(expectedDetail)

        useCase(1).test {
            assertEquals(expectedDetail, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns null when pokemon not found`() = runTest {
        val repository = providesRepository()
        val useCase = providesUseCase(repository)

        coEvery { repository.getPokemonDetail(999) } returns flowOf(null)

        useCase(999).test {
            assertNull(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `refresh calls repository refresh with correct id`() = runTest {
        val repository = providesRepository()
        val useCase = providesUseCase(repository)
        val pokemonId = 25

        coEvery { repository.refreshPokemonDetail(pokemonId) } returns Unit

        useCase.refresh(pokemonId)

        coVerify(exactly = 1) { repository.refreshPokemonDetail(pokemonId) }
    }
}
