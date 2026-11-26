package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapitalRaidSeasonClanInfo(
    val badgeUrls: BadgeUrls,
    val level: Int,
    val name: String,
    val tag: String
)