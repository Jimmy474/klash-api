package com.jimmy.klash.model

import com.jimmy.klash.ClanWarResponseSerializer
import com.jimmy.klash.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable(with = ClanWarResponseSerializer::class)
sealed interface ClanWarResponse{

    val state: ClanWarState

    @Serializable
    data class ClanWar(
        val attacksPerMember: Int? = null,
        val battleModifier: BattleModifier? = null,
        val clan: WarClan,
        @Serializable(with = OffsetDateTimeSerializer::class) val endTime: OffsetDateTime,
        val opponent: WarClan,
        @Serializable(with = OffsetDateTimeSerializer::class) val preparationStartTime: OffsetDateTime,
        @Serializable(with = OffsetDateTimeSerializer::class) val startTime: OffsetDateTime,
        override val state: ClanWarState,
        val teamSize: Int,
        @Serializable(with = OffsetDateTimeSerializer::class) val warStartTime: OffsetDateTime? = null
    ) : ClanWarResponse

    @Serializable
    data class NotInWar(
        val clan: NotInWarClan,
        val opponent: NotInWarClan,
        override val state: ClanWarState,
    ) : ClanWarResponse
}