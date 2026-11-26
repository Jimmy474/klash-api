package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerItemLevel(
    val level: Int,
    val maxLevel: Int,
    val name: String,
    val village: Village,
)