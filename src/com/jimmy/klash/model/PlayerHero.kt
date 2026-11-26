package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerHero(
    val equipment: List<PlayerItemLevel>? = null,
    val level: Int,
    val maxLevel: Int,
    val name: String,
    val village: Village,
)