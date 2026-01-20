package com.example.data.repository

import app.cash.turbine.test
import com.example.data.local.datasource.ILocalDataSource
import com.example.data.local.entity.PokemonDetailEntity
import com.example.data.local.entity.PokemonEntity
import com.example.data.remote.datasource.IRemoteDataSource
import com.example.data.remote.dto.PokemonDetailDto
import com.example.data.remote.dto.PokemonListResponseDto
import com.example.data.remote.dto.PokemonResultDto
import com.example.data.remote.dto.SpritesDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonRepositoryImplTest {

    private fun providesRemoteDataSource(): IRemoteDataSource = mockk(relaxed = true)

    private fun providesLocalDataSource(): ILocalDataSource = mockk(relaxed = true)

    private fun providesRepository(
        remoteDataSource: IRemoteDataSource = providesRemoteDataSource(),
        localDataSource: ILocalDataSource = providesLocalDataSource()
    ): PokemonRepositoryImpl = PokemonRepositoryImpl(remoteDataSource, localDataSource)

    private fun providesPokemonEntity(
        id: Int = 1,
        name: String = "bulbasaur",
        imageUrl: String = "https://example.com/1.png"
    ): PokemonEntity = PokemonEntity(id, name, imageUrl)

    private fun providesPokemonEntityList(count: Int = 3): List<PokemonEntity> =
        (1..count).map { providesPokemonEntity(id = it, name = "pokemon$it") }

    private fun providesPokemonDetailEntity(
        id: Int = 1,
        name: String = "bulbasaur"
    ): PokemonDetailEntity = PokemonDetailEntity(
        id = id,
        name = name,
        imageUrl = "https://example.com/$id.png",
        height = 7,
        weight = 69,
        types = "grass,poison",
        stats = "hp:45,attack:49",
        abilities = "overgrow"
    )

    private fun providesPokemonListResponseDto(): PokemonListResponseDto = PokemonListResponseDto(
        count = 3,
        next = null,
        previous = null,
        results = listOf(
            PokemonResultDto("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
            PokemonResultDto("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")
        )
    )

    private fun providesPokemonDetailDto(id: Int = 1): PokemonDetailDto = PokemonDetailDto(
        id = id,
        name = "bulbasaur",
        height = 7,
        weight = 69,
        sprites = SpritesDto(frontDefault = "https://example.com/1.png", other = null),
        types = emptyList(),
        stats = emptyList(),
        abilities = emptyList()
    )

    @Test
    fun `getPokemonList emits cached data from local data source`() = runTest {
        val localDataSource = providesLocalDataSource()
        val remoteDataSource = providesRemoteDataSource()
        val entities = providesPokemonEntityList(2)
        val repository = providesRepository(remoteDataSource, localDataSource)

        coEvery { localDataSource.getPokemonList() } returns flowOf(entities)
        coEvery { remoteDataSource.getPokemonList(any(), any()) } returns providesPokemonListResponseDto()

        repository.getPokemonList().test {
            val result = awaitItem()
            assertEquals(2, result.size)
            assertEquals("pokemon1", result[0].name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPokemonDetail emits cached data from local data source`() = runTest {
        val localDataSource = providesLocalDataSource()
        val remoteDataSource = providesRemoteDataSource()
        val entity = providesPokemonDetailEntity()
        val repository = providesRepository(remoteDataSource, localDataSource)

        coEvery { localDataSource.getPokemonDetail(1) } returns flowOf(entity)
        coEvery { remoteDataSource.getPokemonDetail(1) } returns providesPokemonDetailDto()

        repository.getPokemonDetail(1).test {
            val result = awaitItem()
            assertEquals("bulbasaur", result?.name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refreshPokemonList fetches from remote and saves to local`() = runTest {
        val localDataSource = providesLocalDataSource()
        val remoteDataSource = providesRemoteDataSource()
        val repository = providesRepository(remoteDataSource, localDataSource)

        coEvery { remoteDataSource.getPokemonList(50, 0) } returns providesPokemonListResponseDto()
        coEvery { localDataSource.savePokemonList(any()) } returns Unit

        repository.refreshPokemonList()

        coVerify { remoteDataSource.getPokemonList(50, 0) }
        coVerify { localDataSource.savePokemonList(any()) }
    }

    @Test
    fun `refreshPokemonDetail fetches from remote and saves to local`() = runTest {
        val localDataSource = providesLocalDataSource()
        val remoteDataSource = providesRemoteDataSource()
        val repository = providesRepository(remoteDataSource, localDataSource)

        coEvery { remoteDataSource.getPokemonDetail(1) } returns providesPokemonDetailDto()
        coEvery { localDataSource.savePokemonDetail(any()) } returns Unit

        repository.refreshPokemonDetail(1)

        coVerify { remoteDataSource.getPokemonDetail(1) }
        coVerify { localDataSource.savePokemonDetail(any()) }
    }
}
