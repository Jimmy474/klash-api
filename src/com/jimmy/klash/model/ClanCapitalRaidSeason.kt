package com.jimmy.klash.model

import com.jimmy.klash.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class ClanCapitalRaidSeason(
    val attackLog: List<ClanCapitalRaidSeasonAttackLogEntry>,
    val capitalTotalLoot: Int,
    val defenseLog: List<ClanCapitalRaidSeasonDefenseLogEntry>,
    val defensiveReward: Int,
    @Serializable(with = OffsetDateTimeSerializer::class) val endTime: OffsetDateTime,
    val enemyDistrictsDestroyed: Int,
    val members: List<ClanCapitalRaidSeasonMember>? = null,
    val offensiveReward: Int,
    val raidsCompleted: Int,
    @Serializable(with = OffsetDateTimeSerializer::class) val startTime: OffsetDateTime,
    val state: ClanCapitalRaidSeasonState,
    val totalAttacks: Int,
)