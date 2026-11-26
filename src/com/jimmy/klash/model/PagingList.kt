package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class PagingList <T> (
    val items: List<T>,
    val paging: Paging
)