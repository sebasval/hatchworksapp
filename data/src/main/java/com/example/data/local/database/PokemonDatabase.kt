package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.PokemonDetailEntity
import com.example.data.local.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class, PokemonDetailEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val DATABASE_NAME = "pokemon_database"
    }
}
