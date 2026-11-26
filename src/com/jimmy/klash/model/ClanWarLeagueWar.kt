package com.jimmy.klash.model

import com.jimmy.klash.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class ClanWarLeagueWar(
    val attacksPerMember: Int? = null,
    val battleModifier: BattleModifier? = null,
    val clan: WarClan,
    @Serializable(with = OffsetDateTimeSerializer::class) val endTime: OffsetDateTime,
    val opponent: WarClan,
    @Serializable(with = OffsetDateTimeSerializer::class) val preparationStartTime: OffsetDateTime,
    @Serializable(with = OffsetDateTimeSerializer::class) val startTime: OffsetDateTime,
    val state: ClanWarState,
    val teamSize: Int,
    @Serializable(with = OffsetDateTimeSerializer::class) val warStartTime: OffsetDateTime? = null,
)