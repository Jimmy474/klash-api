package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class LeagueGroupMember(
    val attackLoseCount: Int,
    val attackWinCount: Int,
    val clanName: String? = null,
    val clanTag: String? = null,
    val defenseLoseCount: Int,
    val defenseWinCount: Int,
    val leagueTrophies: Int,
    val playerName: String,
    val playerTag: String
)