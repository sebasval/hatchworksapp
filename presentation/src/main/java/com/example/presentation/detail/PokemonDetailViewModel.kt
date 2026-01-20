package com.example.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.IGetPokemonDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class PokemonDetailViewModel(
    private val pokemonId: Int,
    private val getPokemonDetailUseCase: IGetPokemonDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    init {
        loadPokemonDetail()
    }

    private fun loadPokemonDetail() {
        getPokemonDetailUseCase(pokemonId)
            .onStart { _uiState.value = PokemonDetailUiState.Loading }
            .onEach { pokemonDetail ->
                if (pokemonDetail != null) {
                    _uiState.value = PokemonDetailUiState.Success(pokemonDetail)
                } else {
                    _uiState.value = PokemonDetailUiState.Error("Pokemon not found")
                }
            }
            .catch { exception ->
                _uiState.value = PokemonDetailUiState.Error(
                    exception.message ?: "Unknown error occurred"
                )
            }
            .launchIn(viewModelScope)
    }
}
