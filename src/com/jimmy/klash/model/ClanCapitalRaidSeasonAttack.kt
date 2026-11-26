package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapitalRaidSeasonAttack(
    val attacker: ClanCapitalRaidSeasonAttacker,
    val stars: Int,
    val destructionPercent: Int,
)