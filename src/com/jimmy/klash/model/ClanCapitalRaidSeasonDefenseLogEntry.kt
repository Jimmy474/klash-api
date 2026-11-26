package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapitalRaidSeasonDefenseLogEntry(
    val attackCount: Int,
    val attacker: ClanCapitalRaidSeasonClanInfo,
    val districtCount: Int,
    val districts: List<ClanCapitalRaidSeasonDistrict>,
    val districtsDestroyed: Int
)