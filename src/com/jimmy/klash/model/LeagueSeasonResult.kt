package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class LeagueSeasonResult(
    val attackLosses: Int,
    val attackStars: Int,
    val attackWins: Int,
    val defenseLosses: Int,
    val defenseStars: Int,
    val defenseWins: Int,
    val leagueSeasonId: Long,
    val leagueTierId: Int,
    val leagueTrophies: Int,
    val maxBattles: Int,
    val placement: Int,
)