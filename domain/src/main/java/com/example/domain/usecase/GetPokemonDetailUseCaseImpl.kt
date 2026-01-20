package com.example.domain.usecase

import com.example.domain.model.PokemonDetail
import com.example.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonDetailUseCaseImpl(
    private val repository: IPokemonRepository
) : IGetPokemonDetailUseCase {

    override fun invoke(id: Int): Flow<PokemonDetail?> {
        return repository.getPokemonDetail(id)
    }

    override suspend fun refresh(id: Int) {
        repository.refreshPokemonDetail(id)
    }
}
