package com.example.data.repository

import com.example.data.local.datasource.ILocalDataSource
import com.example.data.remote.datasource.IRemoteDataSource
import com.example.data.repository.mapper.toDomain
import com.example.data.repository.mapper.toEntity
import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonDetail
import com.example.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class PokemonRepositoryImpl(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : IPokemonRepository {

    override fun getPokemonList(): Flow<List<Pokemon>> {
        return localDataSource.getPokemonList()
            .map { entities -> entities.map { it.toDomain() } }
            .onStart { refreshPokemonListSafely() }
    }

    override fun getPokemonDetail(id: Int): Flow<PokemonDetail?> {
        return localDataSource.getPokemonDetail(id)
            .map { entity -> entity?.toDomain() }
            .onStart { refreshPokemonDetailSafely(id) }
    }

    override suspend fun refreshPokemonList() {
        val response = remoteDataSource.getPokemonList(limit = 50, offset = 0)
        val entities = response.results.map { it.toEntity() }
        localDataSource.savePokemonList(entities)
    }

    override suspend fun refreshPokemonDetail(id: Int) {
        val dto = remoteDataSource.getPokemonDetail(id)
        val entity = dto.toEntity()
        localDataSource.savePokemonDetail(entity)
    }

    private suspend fun refreshPokemonListSafely() {
        try {
            refreshPokemonList()
        } catch (_: Exception) {
        }
    }

    private suspend fun refreshPokemonDetailSafely(id: Int) {
        try {
            refreshPokemonDetail(id)
        } catch (_: Exception) {
        }
    }
}
