package com.jimmy.klash.model

import kotlinx.serialization.SerialName

enum class PlayerHouseElementType{
    @SerialName("ground") Ground,
    @SerialName("roof") Roof,
    @SerialName("walls") Walls,
    @SerialName("decoration") Decoration
}