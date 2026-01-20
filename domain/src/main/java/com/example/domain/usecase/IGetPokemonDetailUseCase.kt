package com.example.domain.usecase

import com.example.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

/**
 * Use case interface for retrieving Pokemon detail information.
 * Encapsulates the business logic for fetching and managing Pokemon detail data.
 */
interface IGetPokemonDetailUseCase {

    /**
     * Executes the use case to retrieve detail information for a specific Pokemon.
     * Returns a Flow that emits cached data first, then updates with fresh network data.
     *
     * @param id The unique identifier of the Pokemon.
     * @return A [Flow] emitting the [PokemonDetail] or null if not found.
     */
    operator fun invoke(id: Int): Flow<PokemonDetail?>

    /**
     * Forces a refresh of the Pokemon detail from the network.
     *
     * @param id The unique identifier of the Pokemon.
     * @throws Exception if the refresh operation fails.
     */
    suspend fun refresh(id: Int)
}
