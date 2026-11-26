package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerRanking(
    val attackWins: Int,
    val clan: ClanReference? = null,
    val defenseWins: Int,
    val expLevel: Int,
    val league: League,
    val leagueTier: LeagueTier,
    val name: String,
    val previousRank: Int,
    val rank: Int,
    val tag: String,
    val trophies: Int,
)