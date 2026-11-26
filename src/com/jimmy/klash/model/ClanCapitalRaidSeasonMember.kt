package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanCapitalRaidSeasonMember(
    val attackLimit: Int,
    val attacks: Int,
    val bonusAttackLimit: Int,
    val capitalResourcesLooted: Int,
    val name: String,
    val tag: String
)