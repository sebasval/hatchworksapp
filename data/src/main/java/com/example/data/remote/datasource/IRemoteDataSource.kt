package com.example.data.remote.datasource

import com.example.data.remote.dto.PokemonDetailDto
import com.example.data.remote.dto.PokemonListResponseDto

/**
 * Interface for remote data source operations.
 * Provides access to Pokemon data from the network API.
 */
interface IRemoteDataSource {

    /**
     * Fetches a paginated list of Pokemon from the remote API.
     *
     * @param limit The maximum number of Pokemon to return.
     * @param offset The starting position for pagination.
     * @return [PokemonListResponseDto] containing the list of Pokemon results.
     * @throws Exception if the network request fails.
     */
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponseDto

    /**
     * Fetches detailed information for a specific Pokemon.
     *
     * @param id The unique identifier of the Pokemon.
     * @return [PokemonDetailDto] containing detailed Pokemon information.
     * @throws Exception if the network request fails or Pokemon is not found.
     */
    suspend fun getPokemonDetail(id: Int): PokemonDetailDto
}
