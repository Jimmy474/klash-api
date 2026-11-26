package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerBuilderBaseRanking(
    val builderBaseLeague: BuilderBaseLeague,
    val builderBaseTrophies: Int,
    val clan: ClanReference? = null,
    val expLevel: Int,
    val name: String,
    val previousRank: Int,
    val rank: Int,
    val tag: String,
)