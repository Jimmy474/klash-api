package com.jimmy.klash

import com.jimmy.klash.api.KlashAPI
import com.jimmy.klash.api.KlashApiException
import com.jimmy.klash.model.ClanWarResponse
import com.jimmy.klash.model.ClanWarState
import com.jimmy.klash.model.VerifyTokenStatus
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

// Run with KLASH_HOSTED_MOCK_TESTS=true ./kotlin test; these tests require network access.
@EnabledIfEnvironmentVariable(named = "KLASH_HOSTED_MOCK_TESTS", matches = "true")
class HostedMockApiTests {
    @Test
    fun playerAndTokenVariants() = runBlocking {
        val api = KlashAPI()
        assertEquals("#2PP", api.getPlayer("#2PP").tag)
        assertEquals(VerifyTokenStatus.Ok, api.verifyPlayerToken("#2PP", "TOKEN").status)
        assertEquals(VerifyTokenStatus.Invalid, api.verifyPlayerToken("#2PP", "invalid").status)
        assertEquals(HttpStatusCode.NotFound, assertFailsWith<KlashApiException> { api.getPlayer("#2PPP") }.statusCode)
        assertEquals(HttpStatusCode.BadRequest, assertFailsWith<KlashApiException> { api.getPlayer("#INVALID") }.statusCode)
    }

    @Test
    fun currentWarVariants() = runBlocking {
        val api = KlashAPI()
        assertEquals(ClanWarState.InWar, api.getClanCurrentWar("#2PP").state)
        assertEquals(ClanWarState.Preparation, api.getClanCurrentWar("#2PPP").state)
        assertEquals(ClanWarState.WarEnded, api.getClanCurrentWar("#2PPPP").state)
        assertIs<ClanWarResponse.NotInWar>(api.getClanCurrentWar("#2PPPPP"))
        assertEquals(HttpStatusCode.Forbidden, assertFailsWith<KlashApiException> { api.getClanCurrentWar("#2PPPPPP") }.statusCode)
    }

    @Test
    fun simulatedHttpFailures() = runBlocking {
        val failures = mapOf(
            "maintenance" to HttpStatusCode.ServiceUnavailable,
            "unavailable" to HttpStatusCode.ServiceUnavailable,
            "rateLimited" to HttpStatusCode.TooManyRequests,
            "notFound" to HttpStatusCode.NotFound,
            "forbidden" to HttpStatusCode.Forbidden,
            "privateWarLog" to HttpStatusCode.Forbidden,
            "invalidIp" to HttpStatusCode.Forbidden,
            "invalidRequest" to HttpStatusCode.BadRequest,
            "internalError" to HttpStatusCode.InternalServerError,
            "notImplemented" to HttpStatusCode.NotImplemented,
        )
        HttpClient().use { client ->
            failures.forEach { (variant, expected) ->
                val response = client.get("https://${KlashAPI.TESTING_URL}/players/%232PP") {
                    header("x-clash-mock-response", variant)
                }
                assertEquals(expected, response.status, variant)
            }
        }
    }
}