package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapitalDistrict(
    val districtHallLevel: Int,
    val id: Int,
    val name: String,
)