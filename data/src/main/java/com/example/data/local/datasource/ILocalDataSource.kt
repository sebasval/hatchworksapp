package com.example.data.local.datasource

import com.example.data.local.entity.PokemonDetailEntity
import com.example.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface for local data source operations.
 * Provides access to cached Pokemon data stored in the local database.
 */
interface ILocalDataSource {

    /**
     * Retrieves the cached list of Pokemon as a Flow.
     * Emits updates whenever the underlying data changes.
     *
     * @return A [Flow] emitting the list of cached [PokemonEntity].
     */
    fun getPokemonList(): Flow<List<PokemonEntity>>

    /**
     * Saves a list of Pokemon to the local database.
     * Existing entries with the same ID will be replaced.
     *
     * @param pokemonList The list of [PokemonEntity] to save.
     */
    suspend fun savePokemonList(pokemonList: List<PokemonEntity>)

    /**
     * Retrieves detailed information for a specific Pokemon as a Flow.
     * Emits null if the Pokemon is not found in the cache.
     *
     * @param id The unique identifier of the Pokemon.
     * @return A [Flow] emitting the [PokemonDetailEntity] or null if not cached.
     */
    fun getPokemonDetail(id: Int): Flow<PokemonDetailEntity?>

    /**
     * Saves Pokemon detail information to the local database.
     * Existing entry with the same ID will be replaced.
     *
     * @param pokemonDetail The [PokemonDetailEntity] to save.
     */
    suspend fun savePokemonDetail(pokemonDetail: PokemonDetailEntity)
}
