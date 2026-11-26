package com.jimmy.klash.model

import kotlinx.serialization.Serializable

@Serializable
data class Paging(val cursors: CursorPaging)

