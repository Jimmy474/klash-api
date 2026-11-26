package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class LegendLeagueTournamentSeasonResult(
    val id: String? = null,
    val rank: Int? = null,
    val trophies: Int,
)