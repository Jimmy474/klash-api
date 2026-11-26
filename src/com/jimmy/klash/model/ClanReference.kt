package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanReference(
    val badgeUrls: BadgeUrls,
    val name: String,
    val tag: String,
)