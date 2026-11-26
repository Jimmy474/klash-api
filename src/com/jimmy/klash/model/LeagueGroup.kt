package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class LeagueGroup(
    val attackLogs: List<LeagueBattleLogEntry>,
    val defenseLogs: List<LeagueBattleLogEntry>,
    val members: List<LeagueGroupMember>
)

