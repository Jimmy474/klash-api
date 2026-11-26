package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanRanking(
    val badgeUrls: BadgeUrls,
    val clanLevel: Int,
    val clanPoints: Int,
    val location: Location? = null,
    val members: Int,
    val name: String,
    val previousRank: Int,
    val rank: Int,
    val tag: String
)