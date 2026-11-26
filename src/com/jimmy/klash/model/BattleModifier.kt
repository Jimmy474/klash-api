package com.jimmy.klash.model

import kotlinx.serialization.SerialName

enum class BattleModifier{
    @SerialName("none") None,
    @SerialName("hardMode") HardMode,
    @SerialName("minusOne") MinusOne,
    @SerialName("minusTwo") MinusTwo,
    @SerialName("minusThree") MinusThree,
}