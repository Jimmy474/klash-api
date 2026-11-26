package com.jimmy.klash.model

import kotlinx.serialization.SerialName

enum class Village{
    @SerialName("home") Home,
    @SerialName("builderBase") BuilderBase,
    @SerialName("clanCapital") ClanCapital
}