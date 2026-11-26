package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class Language(
    val id: Int,
    val languageCode: String,
    val name: String,
)