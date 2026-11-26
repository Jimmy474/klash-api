package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class BuilderBaseLeague(
    val id: Int,
    val name: String,
)