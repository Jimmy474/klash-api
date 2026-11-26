package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerSeasonRanking(
    val attackWins: Int,
    val clan: ClanReference? = null,
    val defenseWins: Int,
    val expLevel: Int,
    val leagueTier: LeagueTier,
    val name: String,
    val rank: Int,
    val tag: String,
    val trophies: Int,
)