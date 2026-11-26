package com.jimmy.klash.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyTokenResponse(
    val tag: String,
    val token: String,
    val status: VerifyTokenStatus
)

enum class VerifyTokenStatus {
    @SerialName("ok") Ok,
    @SerialName("invalid") Invalid,
}