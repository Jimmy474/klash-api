package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapitalRaidSeasonAttackLogEntry(
    val attackCount: Int,
    val defender: ClanCapitalRaidSeasonClanInfo,
    val districtCount: Int,
    val districts: List<ClanCapitalRaidSeasonDistrict>,
    val districtsDestroyed: Int
)