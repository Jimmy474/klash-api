package com.jimmy.klash.model

import kotlinx.serialization.Serializable

// TODO : convert name to enum
@Serializable
data class Resource(
    val amount: Long,
    val name: String,
)