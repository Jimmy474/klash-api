package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapitalRaidSeasonDistrict(
    val attackCount: Int,
    val attacks: List<ClanCapitalRaidSeasonAttack>? = null,
    val destructionPercent: Int,
    val districtHallLevel: Int,
    val id: Int,
    val name: String,
    val stars: Int,
    val totalLooted: Int
)