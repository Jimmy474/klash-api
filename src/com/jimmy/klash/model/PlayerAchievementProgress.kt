package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerAchievementProgress(
    val completionInfo: String? = null,
    val info: String,
    val name: String,
    val stars: Int,
    val target: Int,
    val value: Long,
    val village: Village,
)