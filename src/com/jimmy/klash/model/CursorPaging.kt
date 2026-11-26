package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class CursorPaging(
    val before: String? = null,
    val after: String? = null
)