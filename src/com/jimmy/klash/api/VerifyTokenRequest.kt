package com.jimmy.klash.api

import kotlinx.serialization.Serializable

@Serializable
data class VerifyTokenRequest(val token: String)