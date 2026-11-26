package com.jimmy.klash.model

import com.jimmy.klash.OffsetDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class BattleLogEntry(
    val armyShareCode: String? = null,
    val attack: Boolean,
    val availableLoot: List<Resource>,
    val battleTime: Int,
    @SerialName("battleTimestamp")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val battleTimeStamp: OffsetDateTime,
    val battleType: BattleType,
    val destructionPercentage: Int,
    val extraLootedResources: List<Resource>,
    val lootedResources: List<Resource>,
    val opponentName: String,
    val opponentPlayerTag: String,
    val opponentTownHallLevel: Int,
    val stars: Int,
)