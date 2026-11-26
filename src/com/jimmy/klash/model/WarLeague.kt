package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class WarLeague(
    val id: Int,
    val name: String,
)