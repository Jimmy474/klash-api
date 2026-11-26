package com.jimmy.klash.model

import com.jimmy.klash.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class GoldPassSeason(
    @Serializable(with = OffsetDateTimeSerializer::class) val endTime: OffsetDateTime,
    @Serializable(with = OffsetDateTimeSerializer::class) val startTime: OffsetDateTime,
)