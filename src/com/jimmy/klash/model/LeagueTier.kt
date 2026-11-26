package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class LeagueTier(
    val iconUrls: LeagueTierIconUrls,
    val id: Int,
    val name: String,
)