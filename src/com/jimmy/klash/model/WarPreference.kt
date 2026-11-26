package com.jimmy.klash.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class WarPreference{
    @SerialName("in") In,
    @SerialName("out") Out,
}