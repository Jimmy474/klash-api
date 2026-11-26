package com.jimmy.klash.api

import com.jimmy.klash.model.ClientErrorResponse
import io.ktor.http.HttpStatusCode

class KlashApiException(
    val statusCode: HttpStatusCode,
    val response: ClientErrorResponse
) : KlashException(response.message ?: "Unknown error")