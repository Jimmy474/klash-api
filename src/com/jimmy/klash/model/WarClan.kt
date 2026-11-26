package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class WarClan(
    val attacks: Int,
    val badgeUrls: BadgeUrls,
    val clanLevel: Int,
    val destructionPercentage: Double,
    val members: List<ClanWarMember>,
    val name: String,
    val stars: Int,
    val tag: String,
)