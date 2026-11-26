package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanWarAttack(
    val attackerTag: String,
    val defenderTag: String,
    val destructionPercentage: Int,
    val duration: Int,
    val order: Int,
    val stars: Int,
)