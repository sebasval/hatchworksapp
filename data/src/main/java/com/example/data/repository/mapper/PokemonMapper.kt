package com.example.data.repository.mapper

import com.example.data.local.entity.PokemonDetailEntity
import com.example.data.local.entity.PokemonEntity
import com.example.data.remote.dto.PokemonDetailDto
import com.example.data.remote.dto.PokemonResultDto
import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonDetail
import com.example.domain.model.PokemonStat

internal fun PokemonResultDto.toEntity(): PokemonEntity {
    val id = extractId()
    return PokemonEntity(
        id = id,
        name = name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    )
}

internal fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}

internal fun PokemonDetailDto.toEntity(): PokemonDetailEntity {
    val imageUrl = sprites.other?.officialArtwork?.frontDefault
        ?: sprites.frontDefault
        ?: ""

    return PokemonDetailEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        height = height,
        weight = weight,
        types = types.joinToString(",") { it.type.name },
        stats = stats.joinToString(",") { "${it.stat.name}:${it.baseStat}" },
        abilities = abilities.filter { !it.isHidden }.joinToString(",") { it.ability.name }
    )
}

internal fun PokemonDetailEntity.toDomain(): PokemonDetail {
    return PokemonDetail(
        id = id,
        name = name,
        imageUrl = imageUrl,
        height = height,
        weight = weight,
        types = types.split(",").filter { it.isNotEmpty() },
        stats = stats.split(",").filter { it.isNotEmpty() }.map { statString ->
            val parts = statString.split(":")
            PokemonStat(
                name = parts.getOrElse(0) { "" },
                value = parts.getOrElse(1) { "0" }.toIntOrNull() ?: 0
            )
        },
        abilities = abilities.split(",").filter { it.isNotEmpty() }
    )
}
