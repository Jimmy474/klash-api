package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanWarLeagueGroup(
    val clans: List<ClanWarLeagueClan>? = null,
    val rounds: List<ClanWarLeagueRound>? = null,
    val season: String? = null,
    val state: ClanWarState,
)

