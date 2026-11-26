package com.jimmy.klash.model

import kotlinx.serialization.SerialName

enum class ClanWarResult{
    @SerialName("win") Win,
    @SerialName("lose") Lose,
    @SerialName("tie") Tie
}