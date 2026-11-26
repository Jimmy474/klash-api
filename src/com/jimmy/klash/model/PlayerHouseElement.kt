package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerHouseElement(
    val id: Long,
    val type: PlayerHouseElementType,
)