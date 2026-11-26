package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapital(
    val capitalHallLevel: Int? = null,
    val clanGoldSinkTotal: Long,
    val districts: List<ClanCapitalDistrict>? = null,
)