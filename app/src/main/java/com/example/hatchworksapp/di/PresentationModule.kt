package com.example.hatchworksapp.di

import com.example.presentation.detail.PokemonDetailViewModel
import com.example.presentation.list.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        PokemonListViewModel(getPokemonListUseCase = get())
    }

    viewModel { parameters ->
        PokemonDetailViewModel(
            pokemonId = parameters.get(),
            getPokemonDetailUseCase = get()
        )
    }
}
