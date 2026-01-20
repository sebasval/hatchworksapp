package com.example.data.local.datasource

import com.example.data.local.database.PokemonDao
import com.example.data.local.entity.PokemonDetailEntity
import com.example.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    private val dao: PokemonDao
) : ILocalDataSource {

    override fun getPokemonList(): Flow<List<PokemonEntity>> {
        return dao.getPokemonList()
    }

    override suspend fun savePokemonList(pokemonList: List<PokemonEntity>) {
        dao.insertPokemonList(pokemonList)
    }

    override fun getPokemonDetail(id: Int): Flow<PokemonDetailEntity?> {
        return dao.getPokemonDetail(id)
    }

    override suspend fun savePokemonDetail(pokemonDetail: PokemonDetailEntity) {
        dao.insertPokemonDetail(pokemonDetail)
    }
}
