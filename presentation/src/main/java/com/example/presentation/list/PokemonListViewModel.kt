package com.example.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Pokemon
import com.example.domain.usecase.IGetPokemonListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val getPokemonListUseCase: IGetPokemonListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var allPokemonList: List<Pokemon> = emptyList()

    private val _filteredPokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val filteredPokemonList: StateFlow<List<Pokemon>> = _filteredPokemonList.asStateFlow()

    private val _featuredPokemon = MutableStateFlow<Pokemon?>(null)
    val featuredPokemon: StateFlow<Pokemon?> = _featuredPokemon.asStateFlow()

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() {
        getPokemonListUseCase()
            .onStart { _uiState.value = PokemonListUiState.Loading }
            .onEach { pokemonList ->
                allPokemonList = pokemonList
                _uiState.value = PokemonListUiState.Success(pokemonList)
                selectFeaturedPokemon(pokemonList)
                applyFilter()
            }
            .catch { exception ->
                _uiState.value = PokemonListUiState.Error(
                    exception.message ?: "Unknown error occurred"
                )
            }
            .launchIn(viewModelScope)
    }

    private fun applyFilter() {
        val query = _searchQuery.value
        _filteredPokemonList.value = if (query.isBlank()) {
            allPokemonList
        } else {
            allPokemonList.filter { pokemon ->
                pokemon.name.contains(query, ignoreCase = true) ||
                    pokemon.id.toString().contains(query)
            }
        }
    }

    private fun selectFeaturedPokemon(pokemonList: List<Pokemon>) {
        if (pokemonList.isNotEmpty() && _featuredPokemon.value == null) {
            _featuredPokemon.value = pokemonList.random()
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        applyFilter()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                getPokemonListUseCase.refresh()
            } catch (_: Exception) {
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}
