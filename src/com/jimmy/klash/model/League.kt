package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class League(
    val iconUrls: LeagueIconUrls,
    val id: Int,
    val name: String,
)