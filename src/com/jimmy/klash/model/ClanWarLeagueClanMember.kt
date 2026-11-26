package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanWarLeagueClanMember(
    val name: String,
    val tag: String,
    val townHallLevel: Int
)