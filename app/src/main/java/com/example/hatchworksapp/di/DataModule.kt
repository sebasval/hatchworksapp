package com.example.hatchworksapp.di

import com.example.data.local.datasource.ILocalDataSource
import com.example.data.local.datasource.LocalDataSourceImpl
import com.example.data.remote.datasource.IRemoteDataSource
import com.example.data.remote.datasource.RemoteDataSourceImpl
import com.example.data.repository.PokemonRepositoryImpl
import com.example.domain.repository.IPokemonRepository
import org.koin.dsl.module

val dataModule = module {

    single<IRemoteDataSource> {
        RemoteDataSourceImpl(api = get())
    }

    single<ILocalDataSource> {
        LocalDataSourceImpl(dao = get())
    }

    single<IPokemonRepository> {
        PokemonRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}
