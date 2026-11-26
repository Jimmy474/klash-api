package com.jimmy.klash.model

import kotlinx.serialization.SerialName

enum class ClanWarState{
    @SerialName("notInWar") NotInWar,
    @SerialName("preparation") Preparation,
    @SerialName("inWar") InWar,
    @SerialName("ended") Ended,
    @SerialName("warEnded") WarEnded
}