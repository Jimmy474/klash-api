package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanBuilderBaseRanking(
    val badgeUrls: BadgeUrls,
    val clanBuilderBasePoints: Int,
    val clanLevel: Int,
    val location: Location? = null,
    val members: Int,
    val name: String,
    val previousRank: Int,
    val rank: Int,
    val tag: String,
)