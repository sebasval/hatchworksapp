package com.example.domain.repository

import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Pokemon data operations.
 * Provides unified access to Pokemon data with offline-first strategy.
 */
interface IPokemonRepository {

    /**
     * Retrieves the list of Pokemon with offline-first strategy.
     * First emits cached data from local storage, then fetches fresh data from network
     * and updates the cache.
     *
     * @return A [Flow] emitting the list of [Pokemon].
     */
    fun getPokemonList(): Flow<List<Pokemon>>

    /**
     * Retrieves detailed information for a specific Pokemon with offline-first strategy.
     * First emits cached data if available, then fetches fresh data from network
     * and updates the cache.
     *
     * @param id The unique identifier of the Pokemon.
     * @return A [Flow] emitting the [PokemonDetail] or null if not found.
     */
    fun getPokemonDetail(id: Int): Flow<PokemonDetail?>

    /**
     * Forces a refresh of the Pokemon list from the network.
     * Updates the local cache with fresh data.
     *
     * @throws Exception if the network request fails.
     */
    suspend fun refreshPokemonList()

    /**
     * Forces a refresh of Pokemon detail from the network.
     * Updates the local cache with fresh data.
     *
     * @param id The unique identifier of the Pokemon.
     * @throws Exception if the network request fails.
     */
    suspend fun refreshPokemonDetail(id: Int)
}
