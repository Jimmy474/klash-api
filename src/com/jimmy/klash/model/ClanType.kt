package com.jimmy.klash.model

import kotlinx.serialization.SerialName

enum class ClanType{
    @SerialName("open") Open,
    @SerialName("inviteOnly") InviteOnly,
    @SerialName("closed") Closed
}