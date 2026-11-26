package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerTroop(
    val level: Int,
    val maxLevel: Int,
    val name: String,
    val superTroopIsActive: Boolean? = null,
    val village: Village,
)