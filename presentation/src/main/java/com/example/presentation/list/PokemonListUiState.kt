package com.example.presentation.list

import com.example.domain.model.Pokemon

sealed interface PokemonListUiState {
    data object Loading : PokemonListUiState
    data class Success(val pokemonList: List<Pokemon>) : PokemonListUiState
    data class Error(val message: String) : PokemonListUiState
}
