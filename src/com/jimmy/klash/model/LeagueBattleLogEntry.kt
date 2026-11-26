package com.jimmy.klash.model

import com.jimmy.klash.IsoOffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class LeagueBattleLogEntry(
    @Serializable(with = IsoOffsetDateTimeSerializer::class) val creationTime: OffsetDateTime,
    val destructionPercentage: Int,
    val opponentName: String,
    val opponentPlayerTag: String,
    val stars: Int,
    val trophies: Int
)