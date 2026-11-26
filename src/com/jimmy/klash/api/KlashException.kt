package com.jimmy.klash.api

open class KlashException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)