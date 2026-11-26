package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val countryCode: String? = null,
    val id: Int,
    val isCountry: Boolean,
    val name: String,
)