package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class Label(
    val iconUrls: LabelIconUrls,
    val id: Int,
    val name: String,
)