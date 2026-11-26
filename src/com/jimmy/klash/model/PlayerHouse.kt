package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerHouse(
    val elements: List<PlayerHouseElement>,
)