package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ClanMember(
    val builderBaseLeague: BuilderBaseLeague,
    val builderBaseTrophies: Int,
    val clanRank: Int,
    val donations: Int,
    val donationsReceived: Int,
    val expLevel: Int,
    val league: League,
    val leagueTier: LeagueTier,
    val name: String,
    val playerHouse: PlayerHouse? = null,
    val previousClanRank: Int,
    val role: Role,
    val tag: String,
    val townHallLevel: Int,
    val trophies: Int,
)