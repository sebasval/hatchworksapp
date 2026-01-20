package com.example.data.remote.datasource

import com.example.data.remote.api.PokemonApi
import com.example.data.remote.dto.PokemonDetailDto
import com.example.data.remote.dto.PokemonListResponseDto

class RemoteDataSourceImpl(
    private val api: PokemonApi
) : IRemoteDataSource {

    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponseDto {
        return api.getPokemonList(limit, offset)
    }

    override suspend fun getPokemonDetail(id: Int): PokemonDetailDto {
        return api.getPokemonDetail(id)
    }
}
