package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class BadgeUrls(
    val small: String,
    val medium: String,
    val large: String,
)

@Serializable
data class LeagueTierIconUrls(
    val small: String,
    val large: String,
)

@Serializable
data class LeagueIconUrls(
    val tiny: String,
    val small: String,
    val medium: String? = null,
)

@Serializable
data class LabelIconUrls(
    val small: String,
    val medium: String,
)