package com.jimmy.klash.model

import com.jimmy.klash.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class ClanWarLogEntry(
    val attacksPerMember: Int,
    val battleModifier: BattleModifier,
    val clan: WarLogClan,
    @Serializable(with = OffsetDateTimeSerializer::class) val endTime: OffsetDateTime,
    val opponent: WarLogOpponent,
    val result: ClanWarResult? = null,
    val teamSize: Int
)