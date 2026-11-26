package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerLegendStatistics(
    val bestBuilderBaseSeason: LegendLeagueTournamentSeasonResult? = null,
    val bestSeason: LegendLeagueTournamentSeasonResult? = null,
    val currentSeason: LegendLeagueTournamentSeasonResult,
    val legendTrophies: Int,
    val previousBuilderBaseSeason: LegendLeagueTournamentSeasonResult? = null,
    val previousSeason: LegendLeagueTournamentSeasonResult? = null
)