package com.example.hatchworksapp.di

import com.example.domain.usecase.GetPokemonDetailUseCaseImpl
import com.example.domain.usecase.GetPokemonListUseCaseImpl
import com.example.domain.usecase.IGetPokemonDetailUseCase
import com.example.domain.usecase.IGetPokemonListUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<IGetPokemonListUseCase> {
        GetPokemonListUseCaseImpl(repository = get())
    }

    factory<IGetPokemonDetailUseCase> {
        GetPokemonDetailUseCaseImpl(repository = get())
    }
}
