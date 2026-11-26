package com.jimmy.klash.model

import kotlinx.serialization.SerialName

enum class BattleType{
    @SerialName("ranked") Ranked,
    @SerialName("legend") Legend,
    @SerialName("homeVillage") HomeVillage,
}