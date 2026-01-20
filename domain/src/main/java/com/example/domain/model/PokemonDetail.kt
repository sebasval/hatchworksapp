package com.example.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val stats: List<PokemonStat>,
    val abilities: List<String>
)

data class PokemonStat(
    val name: String,
    val value: Int
)
