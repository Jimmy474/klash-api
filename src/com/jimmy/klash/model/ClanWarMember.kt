package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanWarMember(
    val attacks: List<ClanWarAttack>? = null,
    val bestOpponentAttack: ClanWarAttack? = null,
    val mapPosition: Int,
    val name: String,
    val opponentAttacks: Int,
    val tag: String,
    val townhallLevel: Int,
)