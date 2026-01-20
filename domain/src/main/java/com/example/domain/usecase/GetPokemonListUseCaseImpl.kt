package com.example.domain.usecase

import com.example.domain.model.Pokemon
import com.example.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCaseImpl(
    private val repository: IPokemonRepository
) : IGetPokemonListUseCase {

    override fun invoke(): Flow<List<Pokemon>> {
        return repository.getPokemonList()
    }

    override suspend fun refresh() {
        repository.refreshPokemonList()
    }
}
