package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class ItemsList<T>(val items: List<T>)