package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class NotInWarClan(
    val attacks: Int,
    val badgeUrls: BadgeUrls,
    val clanLevel: Int,
    val destructionPercentage: Double,
    val stars: Int,
)