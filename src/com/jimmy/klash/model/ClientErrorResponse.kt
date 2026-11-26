package com.jimmy.klash.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ClientErrorResponse(
    @Contextual val details: Any? = null,
    val message: String? = null,
    val reason: String,
    val type: String? = null,
)