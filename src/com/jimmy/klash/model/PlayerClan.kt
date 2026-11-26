package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerClan(
    val tag: String,
    val name: String,
    val clanLevel: Int,
    val badgeUrls: BadgeUrls,
)