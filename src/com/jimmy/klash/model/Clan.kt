package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class Clan(
    val badgeUrls: BadgeUrls,
    val capitalLeague: CapitalLeague,
    val chatLanguage: Language? = null,
    val clanBuilderBasePoints: Int,
    val clanCapital: ClanCapital,
    val clanCapitalPoints: Int,
    val clanLevel: Int,
    val clanPoints: Int,
    val description: String,
    val isFamilyFriendly: Boolean,
    val isWarLogPublic: Boolean,
    val labels: List<Label>,
    val location: Location? = null,
    val memberList: List<ClanMember>,
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