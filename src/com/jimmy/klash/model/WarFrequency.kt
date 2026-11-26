package com.jimmy.klash.model

import kotlinx.serialization.SerialName

enum class WarFrequency{
    @SerialName("unknown") Unknown,
    @SerialName("always") Always,
    @SerialName("moreThanOncePerWeek") MoreThanOncePerWeek,
    @SerialName("oncePerWeek") OncePerWeek,
    @SerialName("lessThanOncePerWeek") LessThanOncePerWeek,
    @SerialName("never") Never,
    @SerialName("any") Any
}