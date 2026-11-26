package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class CapitalLeague(
    val id: Int,
    val name: String
)