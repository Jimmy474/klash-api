package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanWarLeagueClan(
    val badgeUrls: BadgeUrls? = null,
    val clanLevel: Int,
    val members: List<ClanWarLeagueClanMember>? = null,
    val name: String,
    val tag: String
)