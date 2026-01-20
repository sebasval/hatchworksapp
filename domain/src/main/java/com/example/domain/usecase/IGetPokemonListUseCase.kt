package com.example.domain.usecase

import com.example.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

/**
 * Use case interface for retrieving the Pokemon list.
 * Encapsulates the business logic for fetching and managing Pokemon list data.
 */
interface IGetPokemonListUseCase {

    /**
     * Executes the use case to retrieve the Pokemon list.
     * Returns a Flow that emits cached data first, then updates with fresh network data.
     *
     * @return A [Flow] emitting the list of [Pokemon].
     */
    operator fun invoke(): Flow<List<Pokemon>>

    /**
     * Forces a refresh of the Pokemon list from the network.
     *
     * @throws Exception if the refresh operation fails.
     */
    suspend fun refresh()
}
