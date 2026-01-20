package com.example.data.remote.datasource

import com.example.data.remote.api.PokemonApi
import com.example.data.remote.dto.PokemonDetailDto
import com.example.data.remote.dto.PokemonListResponseDto
import com.example.data.remote.dto.PokemonResultDto
import com.example.data.remote.dto.SpritesDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RemoteDataSourceImplTest {

    private fun providesApi(): PokemonApi = mockk()

    private fun providesDataSource(
        api: PokemonApi = providesApi()
    ): RemoteDataSourceImpl = RemoteDataSourceImpl(api)

    private fun providesPokemonListResponseDto(
        count: Int = 2
    ): PokemonListResponseDto = PokemonListResponseDto(
        count = count,
        next = null,
        previous = null,
        results = (1..count).map {
            PokemonResultDto("pokemon$it", "https://pokeapi.co/api/v2/pokemon/$it/")
        }
    )

    private fun providesPokemonDetailDto(
        id: Int = 1,
        name: String = "bulbasaur"
    ): PokemonDetailDto = PokemonDetailDto(
        id = id,
        name = name,
        height = 7,
        weight = 69,
        sprites = SpritesDto(frontDefault = "https://example.com/$id.png", other = null),
        types = emptyList(),
        stats = emptyList(),
        abilities = emptyList()
    )

    @Test
    fun `getPokemonList calls api with correct parameters`() = runTest {
        val api = providesApi()
        val dataSource = providesDataSource(api)
        val expectedResponse = providesPokemonListResponseDto()

        coEvery { api.getPokemonList(20, 0) } returns expectedResponse

        val result = dataSource.getPokemonList(20, 0)

        assertEquals(expectedResponse, result)
        coVerify { api.getPokemonList(20, 0) }
    }

    @Test
    fun `getPokemonDetail calls api with correct id`() = runTest {
        val api = providesApi()
        val dataSource = providesDataSource(api)
        val expectedResponse = providesPokemonDetailDto(id = 25, name = "pikachu")

        coEvery { api.getPokemonDetail(25) } returns expectedResponse

        val result = dataSource.getPokemonDetail(25)

        assertEquals(expectedResponse, result)
        coVerify { api.getPokemonDetail(25) }
    }
}
