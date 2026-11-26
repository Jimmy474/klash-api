package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapitalRanking(
    val badgeUrls: BadgeUrls,
    val clanCapitalPoints: Int,
    val clanLevel: Int,
    val location: Location? = null,
    val members: Int,
    val name: String,
    val previousRank: Int,
    val rank: Int,
    val tag: String,
)