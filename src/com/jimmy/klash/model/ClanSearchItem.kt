package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanSearchItem(
    val badgeUrls: BadgeUrls,
    val capitalLeague: CapitalLeague,
    val chatLanguage: Language? = null,
    val clanBuilderBasePoints: Int,
    val clanCapitalPoints: Int,
    val clanLevel: Int,
    val clanPoints: Int,
    val isFamilyFriendly: Boolean,
    val isWarLogPublic: Boolean,
    val labels: List<Label>,
    val location: Location? = null,
    val members: Int,
    val name: String,
    val requiredBuilderBaseTrophies: Int,
    val requiredLeagueTier: LeagueTier? = null,
    val requiredTownhallLevel: Int,
    val requiredTrophies: Int,
    val tag: String,
    val type: ClanType,
    val warFrequency: WarFrequency,
    val warLeague: WarLeague,
    val warLosses: Int? = null,
    val warTies: Int? = null,
    val warWinStreak: Int,
    val warWins: Int,
)