package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class WarLogClan(
    val attacks: Int,
    val badgeUrls: BadgeUrls,
    val clanLevel: Int,
    val destructionPercentage: Double,
    val expEarned: Int,
    val name: String,
    val stars: Int,
    val tag: String
)