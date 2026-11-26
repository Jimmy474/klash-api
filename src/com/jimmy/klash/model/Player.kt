package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val achievements: List<PlayerAchievementProgress>,
    val attackWins: Int,
    val bestBuilderBaseTrophies: Int,
    val bestTrophies: Int,
    val builderBaseLeague: BuilderBaseLeague? = null,
    val builderBaseTrophies: Int? = null,
    val builderHallLevel: Int? = null,
    val clan: PlayerClan? = null,
    val clanCapitalContributions: Int,
    val currentLeagueGroupTag: String? = null,
    val currentLeagueSeasonId: Long? = null,
    val defenseWins: Int,
    val donations: Int,
    val donationsReceived: Int,
    val expLevel: Int,
    val heroEquipment: List<PlayerItemLevel>,
    val heroes: List<PlayerHero>,
    val labels: List<Label>,
    val league: League? = null,
    val leagueTier: LeagueTier,
    val legendStatistics: PlayerLegendStatistics? = null,
    val name: String,
    val playerHouse: PlayerHouse? = null,
    val previousLeagueGroupTag: String? = null,
    val previousLeagueSeasonId: Long,
    val role: Role? = null,
    val spells: List<PlayerItemLevel>,
    val tag: String,
    val townHallLevel: Int,
    val townHallWeaponLevel: Int? = null,
    val troops: List<PlayerTroop>,
    val trophies: Int,
    val warPreference: WarPreference? = null,
    val warStars: Int,
)

