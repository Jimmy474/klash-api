package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class WarLogOpponent(
    val badgeUrls: BadgeUrls,
    val clanLevel: Int,
    val destructionPercentage: Double,
    val name: String? = null,
    val stars: Int,
    val tag: String? = null
)